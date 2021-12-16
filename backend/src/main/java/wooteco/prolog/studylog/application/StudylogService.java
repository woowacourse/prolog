package wooteco.prolog.studylog.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.BaseEntity;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.*;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.exception.StudylogArgumentException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    private final StudylogRepository studylogRepository;
    private final StudylogScrapRepository studylogScrapRepository;
    private final MemberTagService memberTagService;
    private final DocumentService studylogDocumentService;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;

    public StudylogsResponse findStudylogs(StudylogsSearchRequest request, Long memberId, boolean isAnonymousMember) {
        StudylogsResponse studylogs = findStudylogs(request, memberId);
        if (isAnonymousMember) {
            return studylogs;
        }

        List<StudylogResponse> data = studylogs.getData();
        updateScrap(data, findScrapIds(memberId));
        return studylogs;
    }

    public StudylogsResponse findStudylogs(StudylogsSearchRequest request, Long memberId) {
        if (Objects.nonNull(request.getIds())) {
            final Pageable pageable = request.getPageable();

            List<Long> ids = request.getIds();

            int start = (int) pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), ids.size());

            final Map<Long, Studylog> idAndStudylog = studylogRepository
                .findByIdIn(ids.subList(start, end))
                .stream()
                .collect(toMap(BaseEntity::getId, Function.identity()));

            final List<Studylog> studylogs = ids.subList(start, end).stream()
                .map(idAndStudylog::get)
                .collect(toList());

            return StudylogsResponse
                .of(new PageImpl<>(studylogs, pageable, request.getIds().size()), memberId);
        }

        if (request.getKeyword() == null || request.getKeyword().isEmpty()) {
            return findPostsWithoutKeyword(request.getLevels(), request.getMissions(),
                request.getTags(),
                request.getUsernames(), request.getMembers(), request.getStartDate(),
                request.getEndDate(),
                request.getPageable(), memberId);
        }

        final StudylogDocumentResponse response = studylogDocumentService.findBySearchKeyword(
            request.getKeyword(),
            request.getTags(),
            request.getMissions(),
            request.getLevels(),
            request.getUsernames(),
            request.getStartDate(),
            request.getEndDate(),
            request.getPageable()
        );

        final List<Studylog> studylogs = studylogRepository.findAllByIdInOrderByIdDesc(response.getStudylogIds());
        return StudylogsResponse.of(
            studylogs,
            response.getTotalSize(),
            response.getTotalPage(),
            response.getCurrPage(),
            memberId
        );
    }

    public StudylogsResponse findPostsWithoutKeyword(
        List<Long> levelIds,
        List<Long> missionIds,
        List<Long> tagIds,
        List<String> usernames,
        List<Long> members,
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable,
        Long memberId
    ) {

        Specification<Studylog> specs =
            StudylogSpecification.findByLevelIn(levelIds)
                .and(StudylogSpecification.equalIn("mission", missionIds))
                .and(StudylogSpecification.findByTagIn(tagIds))
                .and(StudylogSpecification.findByUsernameIn(usernames))
                .and(StudylogSpecification.findByMemberIn(members))
                .and(StudylogSpecification.findBetweenDate(startDate, endDate))
                .and(StudylogSpecification.distinct(true));

        Page<Studylog> posts = studylogRepository.findAll(specs, pageable);
        return StudylogsResponse.of(posts, memberId);
    }

    public StudylogsResponse findStudylogsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(
            studylogRepository.findByMember(member, pageable),
            member.getId()
        );
    }

    @Transactional
    public List<StudylogResponse> insertStudylogs(Long memberId,
                                                  List<StudylogRequest> studylogRequests) {
        if (studylogRequests.size() == 0) {
            throw new StudylogArgumentException();
        }

        return studylogRequests.stream()
            .map(studylogRequest -> insertStudylog(memberId, studylogRequest))
            .collect(toList());
    }

    private StudylogResponse insertStudylog(Long memberId, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(memberId);
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Mission mission = missionService.findById(studylogRequest.getMissionId());

        Studylog requestedStudylog = new Studylog(foundMember,
                                                  studylogRequest.getTitle(),
                                                  studylogRequest.getContent(),
                                                  mission,
                                                  tags.getList());

        Studylog createdStudylog = studylogRepository.save(requestedStudylog);
        memberTagService.registerMemberTag(tags, foundMember);

        studylogDocumentService.save(createdStudylog.toStudylogDocument());
        return StudylogResponse.of(createdStudylog);
    }

    @Transactional
    public StudylogResponse findById(Long id, Long memberId, boolean isAnonymousMember) {
        increaseViewCount(id, memberId, isAnonymousMember);

        Studylog studylog = findById(id);
        StudylogResponse studylogResponse = StudylogResponse.of(studylog);

        if (isAnonymousMember) {
            return studylogResponse;
        }

        updateScrap(singletonList(studylogResponse), findScrapIds(memberId));
        studylogResponse.setLiked(studylog.likedByMember(memberId));
        return studylogResponse;
    }

    public StudylogResponse findbyIdAndReturnStudylogResponse(Long id) {
        return StudylogResponse.of(findById(id));
    }

    private Studylog findById(Long id) {
        return studylogRepository.findById(id)
            .orElseThrow(StudylogNotFoundException::new);
    }

    private void increaseViewCount(Long id, Long memberId, boolean isAnonymousMember) {
        Studylog studylog = studylogRepository.findById(id)
                .orElseThrow(StudylogNotFoundException::new);

        if (isAnonymousMember) {
            studylog.increaseViewCount();
            return;
        }

        Member foundMember = memberService.findById(memberId);
        studylog.increaseViewCount(foundMember);
    }

    @Transactional
    public void updateStudylog(Long memberId, Long studylogId, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(memberId);

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(foundMember);
        final Tags originalTags = tagService.findByPostAndMember(studylog, foundMember);

        Mission mission = missionService.findById(studylogRequest.getMissionId());
        Tags newTags = tagService.findOrCreate(studylogRequest.getTags());
        studylog.update(studylogRequest.getTitle(), studylogRequest.getContent(), mission, newTags);
        memberTagService.updateMemberTag(originalTags, newTags, foundMember);

        studylogDocumentService.update(studylog.toStudylogDocument());
    }

    @Transactional
    public void deleteStudylog(Long memberId, Long studylogId) {
        final Member foundMember = memberService.findById(memberId);
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(foundMember);

        final Tags tags = tagService.findByPostAndMember(studylog, foundMember);
        studylogDocumentService.delete(studylog.toStudylogDocument());
        studylogRepository.delete(studylog);
        memberTagService.removeMemberTag(tags, foundMember);
    }

    public List<CalendarStudylogResponse> findCalendarPosts(String username, LocalDate localDate) {
        final Member member = memberService.findByUsername(username);
        final LocalDateTime start = localDate.with(firstDayOfMonth()).atStartOfDay();
        final LocalDateTime end = localDate.with(lastDayOfMonth()).atTime(LocalTime.MAX);

        return studylogRepository.findByMemberBetween(member, start, end)
            .stream()
            .map(CalendarStudylogResponse::of)
            .collect(toList());
    }

    private List<Long> findScrapIds(Long memberId) {
        List<StudylogScrap> memberScraps = studylogScrapRepository.findByMemberId(memberId);
        return memberScraps.stream()
            .map(StudylogScrap::getStudylog)
            .map(Studylog::getId)
            .collect(toList());
    }

    private void updateScrap(List<StudylogResponse> studylogs, List<Long> scrapIds) {
        studylogs.forEach(studylogResponse -> {
            if (scrapIds.stream().anyMatch(id -> id.equals(studylogResponse.getId()))) {
                studylogResponse.setScrap(true);
            }
        });
    }
}
