package wooteco.prolog.studylog.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.exception.StudylogArgumentException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    public static final String ID_DELIMITER = ",";
    private final StudylogRepository studylogRepository;
    private final MemberTagService memberTagService;
    private final DocumentService studylogDocumentService;
    private final MissionService missionService;
    private final MemberService memberService;
    private final TagService tagService;

    public StudylogsResponse findStudylogs(StudylogsSearchRequest request) {
        if(Objects.nonNull(request.getIds())) {
            final Pageable pageable = request.getPageable();

            List<Long> ids = request.getIds();

            int start = (int)pageable.getOffset();
            int end = Math.min((start + pageable.getPageSize()), ids.size());

            final Map<Long, Studylog> idAndStudylog = studylogRepository
                .findByIdIn(ids.subList(start, end))
                .stream()
                .collect(toMap(BaseEntity::getId, Function.identity()));

            final List<Studylog> studylogs = ids.subList(start, end).stream()
                .map(idAndStudylog::get)
                .collect(toList());

            return StudylogsResponse.of(new PageImpl<>(studylogs));
        }

        if (request.getKeyword() == null || request.getKeyword().isEmpty()) {
            return findPostsWithoutKeyword(request.getLevels(), request.getMissions(),
                request.getTags(),
                request.getUsernames(), request.getStartDate(), request.getEndDate(),
                request.getPageable());
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

        final List<Studylog> studylogs = studylogRepository.findAllById(response.getStudylogIds());
        return StudylogsResponse.of(studylogs,
            response.getTotalSize(),
            response.getTotalPage(),
            response.getCurrPage()
        );
    }

    public StudylogsResponse findPostsWithoutKeyword(
        List<Long> levelIds,
        List<Long> missionIds,
        List<Long> tagIds,
        List<String> usernames,
        LocalDate startDate,
        LocalDate endDate,
        Pageable pageable) {

        Specification<Studylog> specs =
            StudylogSpecification.findByLevelIn(levelIds)
                .and(StudylogSpecification.equalIn("mission", missionIds))
                .and(StudylogSpecification.findByTagIn(tagIds))
                .and(StudylogSpecification.findByUsernameIn(usernames))
                .and(StudylogSpecification.findBetweenDate(startDate, endDate))
                .and(StudylogSpecification.distinct(true));

        Page<Studylog> posts = studylogRepository.findAll(specs, pageable);

        return StudylogsResponse.of(posts);
    }

    public StudylogsResponse findStudylogsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(studylogRepository.findByMember(member, pageable));
    }

    @Transactional
    public List<StudylogResponse> insertStudylogs(Member member,
                                                  List<StudylogRequest> studylogRequests) {
        if (studylogRequests.size() == 0) {
            throw new StudylogArgumentException();
        }

        return studylogRequests.stream()
            .map(studylogRequest -> insertStudylog(member, studylogRequest))
            .collect(toList());
    }

    private StudylogResponse insertStudylog(Member member, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(member.getId());
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

    public StudylogResponse findById(Long id) {
        Studylog studylog = studylogRepository.findById(id)
            .orElseThrow(StudylogNotFoundException::new);

        return StudylogResponse.of(studylog);
    }

    @Transactional
    public void updateStudylog(Member member, Long studylogId, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(member.getId());

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(member);
        final Tags originalTags = tagService.findByPostAndMember(studylog, foundMember);

        Mission mission = missionService.findById(studylogRequest.getMissionId());
        Tags newTags = tagService.findOrCreate(studylogRequest.getTags());
        studylog.update(studylogRequest.getTitle(), studylogRequest.getContent(), mission, newTags);
        memberTagService.updateMemberTag(originalTags, newTags, foundMember);

        studylogDocumentService.update(studylog.toStudylogDocument());
    }

    @Transactional
    public void deleteStudylog(Member member, Long studylogId) {
        final Member foundMember = memberService.findById(member.getId());
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(foundMember);

        final Tags tags = tagService.findByPostAndMember(studylog, foundMember);
        studylogDocumentService.delete(studylog.toStudylogDocument());
        studylogRepository.delete(studylog);
        memberTagService.removeMemberTag(tags, member);
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
}
