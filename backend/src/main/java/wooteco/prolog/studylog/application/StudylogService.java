package wooteco.prolog.studylog.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.MostPopularStudylog;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogRead;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.MostPopularStudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogReadRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.exception.StudylogArgumentException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.prolog.studylog.exception.StudylogReadNotExistException;
import wooteco.prolog.studylog.exception.StudylogScrapNotExistException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    private static final int A_WEEK = 7;

    private final StudylogRepository studylogRepository;
    private final StudylogScrapRepository studylogScrapRepository;
    private final StudylogReadRepository studylogReadRepository;
    private final MostPopularStudylogRepository mostPopularStudylogRepository;
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
        updateRead(data, findReadIds(memberId));
        return studylogs;
    }

    public void updateMostPopularStudylogs(Pageable pageable) {
        deleteAllLegacyMostPopularStudylogs();

        List<Studylog> studylogs = findStudylogsByDays(pageable, LocalDateTime.now());
        List<MostPopularStudylog> mostPopularStudylogs = studylogs.stream()
            .map(it -> new MostPopularStudylog(it.getId()))
            .collect(toList());

        mostPopularStudylogRepository.saveAll(mostPopularStudylogs);
    }

    private void deleteAllLegacyMostPopularStudylogs() {
        List<MostPopularStudylog> mostPopularStudylogs = mostPopularStudylogRepository.findAllByDeletedFalse();

        for (MostPopularStudylog mostPopularStudylog : mostPopularStudylogs) {
            mostPopularStudylog.delete();
        }
    }

    public StudylogsResponse findMostPopularStudylogs(
        Pageable pageable,
        Long memberId,
        boolean isAnonymousMember
    ) {
        List<Studylog> studylogs = getSortedMostPopularStudyLogs();
        PageImpl<Studylog> page = new PageImpl<>(studylogs, pageable, studylogs.size());
        StudylogsResponse studylogsResponse = StudylogsResponse.of(page, memberId);

        if (isAnonymousMember) {
            return studylogsResponse;
        }
        List<StudylogResponse> data = studylogsResponse.getData();
        updateScrap(data, findScrapIds(memberId));
        updateRead(data, findReadIds(memberId));
        return studylogsResponse;
    }

    private List<Studylog> getSortedMostPopularStudyLogs() {
        List<Long> studylogIds = getMostPopularStudylogIds();
        List<Studylog> studylogs = studylogRepository.findAllById(studylogIds);

        List<Studylog> sortedStudylogs = new ArrayList<>();
        for (Long studylogId : studylogIds) {
            Studylog sortStudylog = studylogs.stream()
                .filter(studylog -> Objects.equals(studylog.getId(), studylogId))
                .findAny()
                .orElseThrow(StudylogNotFoundException::new);

            sortedStudylogs.add(sortStudylog);
        }

        return sortedStudylogs;
    }

    private List<Long> getMostPopularStudylogIds() {
        List<MostPopularStudylog> mostPopularStudylogs = mostPopularStudylogRepository.findAllByDeletedFalse();
        return mostPopularStudylogs.stream()
            .map(MostPopularStudylog::getStudylogId)
            .collect(toList());
    }

    private List<Studylog> findStudylogsByDays(Pageable pageable, LocalDateTime dateTime) {
        int decreaseDays = 0;
        int searchFailedCount = 0;

        while (true) {
            decreaseDays += A_WEEK;
            List<Studylog> studylogs = studylogRepository.findByPastDays(dateTime.minusDays(decreaseDays));

            if (studylogs.size() >= pageable.getPageSize()) {
                return studylogs.stream()
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList())
                    .subList(0, pageable.getPageSize());
            }

            if (searchFailedCount >= 2) {
                return studylogs.stream()
                    .sorted(Comparator.comparing(Studylog::getPopularScore).reversed())
                    .collect(toList());
            }

            searchFailedCount += 1;
        }
    }

    public StudylogsResponse findStudylogs(StudylogsSearchRequest request, Long memberId) {
        if (Objects.nonNull(request.getIds())) {
            Pageable pageable = request.getPageable();
            List<Long> ids = request.getIds();

            Page<Studylog> studylogs = studylogRepository.findByIdInOrderByIdAsc(ids, pageable);

            return StudylogsResponse.of(studylogs, memberId);
        }

        if (request.getKeyword() == null || request.getKeyword().isEmpty()) {
            return findStudylogsWithoutKeyword(request.getLevels(), request.getMissions(),
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

    public StudylogsResponse findStudylogsWithoutKeyword(
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

        Page<Studylog> studylogs = studylogRepository.findAll(specs, pageable);
        return StudylogsResponse.of(studylogs, memberId);
    }

    public StudylogsResponse findStudylogsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(
            studylogRepository.findByMember(member, pageable),
            member.getId()
        );
    }

    public List<Studylog> findStudylogsByUsername(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return studylogRepository.findByMember(member, pageable).getContent();
    }

    @Transactional
    public List<StudylogResponse> insertStudylogs(Long memberId, List<StudylogRequest> studylogRequests) {
        if (studylogRequests.isEmpty()) {
            throw new StudylogArgumentException();
        }

        return studylogRequests.stream()
            .map(studylogRequest -> insertStudylog(memberId, studylogRequest))
            .collect(toList());
    }

    private StudylogResponse insertStudylog(Long memberId, StudylogRequest studylogRequest) {
        Member member = memberService.findById(memberId);
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Mission mission = missionService.findById(studylogRequest.getMissionId());

        Studylog requestedStudylog = new Studylog(member,
            studylogRequest.getTitle(),
            studylogRequest.getContent(),
            mission,
            tags.getList());

        Studylog createdStudylog = studylogRepository.save(requestedStudylog);

        onStudylogCreatedEvent(member, tags, mission, createdStudylog);

        return StudylogResponse.of(createdStudylog);
    }

    private void onStudylogCreatedEvent(Member foundMember, Tags tags, Mission mission, Studylog createdStudylog) {
        memberTagService.registerMemberTag(tags, foundMember);
        studylogDocumentService.save(createdStudylog.toStudylogDocument());
    }

    @Transactional
    public StudylogResponse retrieveStudylogById(LoginMember loginMember, Long studylogId) {
        Studylog studylog = findStudylogById(studylogId);

        onStudylogRetrieveEvent(loginMember, studylog);

        StudylogResponse studylogResponse = toStudylogResponse(loginMember, studylog);

        return studylogResponse;
    }

    private void onStudylogRetrieveEvent(LoginMember loginMember, Studylog studylog) {
        // view 증가
        increaseViewCount(loginMember, studylog);
        if (loginMember.isAnonymous()) {
            return;
        }

        // 읽음 처리
        if (!studylogReadRepository.existsByMemberIdAndStudylogId(loginMember.getId(), studylog.getId())) {
            insertStudylogRead(studylog.getId(), loginMember.getId());
        }
    }

    private StudylogResponse toStudylogResponse(LoginMember loginMember, Studylog studylog) {
        boolean liked = studylog.likedByMember(loginMember.getId());
        boolean read = studylogReadRepository.findByMemberIdAndStudylogId(loginMember.getId(), studylog.getId()).isPresent();
        boolean scraped = studylogScrapRepository.findByMemberIdAndStudylogId(loginMember.getId(), studylog.getId()).isPresent();

        return StudylogResponse.of(studylog, scraped, read, liked);
    }


    public StudylogResponse findByIdAndReturnStudylogResponse(Long id) {
        return StudylogResponse.of(findStudylogById(id));
    }

    private void insertStudylogRead(Long id, Long memberId) {
        Member readMember = memberService.findById(memberId);
        Studylog readStudylog = studylogRepository.findById(id)
            .orElseThrow(StudylogNotFoundException::new);
        studylogReadRepository.save(new StudylogRead(readMember, readStudylog));
    }

    public Studylog findStudylogById(Long id) {
        return studylogRepository.findById(id).orElseThrow(StudylogNotFoundException::new);
    }

    private void increaseViewCount(LoginMember loginMember, Studylog studylog) {
        if (loginMember.isAnonymous()) {
            studylog.increaseViewCount();
            return;
        }

        Member foundMember = memberService.findById(loginMember.getId());
        studylog.increaseViewCount(foundMember);
    }

    @Transactional
    public void updateStudylog(Long memberId, Long studylogId, StudylogRequest studylogRequest) {
        final Member foundMember = memberService.findById(memberId);

        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(StudylogNotFoundException::new);
        studylog.validateAuthor(foundMember);
        final Tags originalTags = tagService.findByStudylogsAndMember(studylog, foundMember);

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

        final Tags tags = tagService.findByStudylogsAndMember(studylog, foundMember);
        studylogDocumentService.delete(studylog.toStudylogDocument());
        checkScrapedOrRead(memberId, studylogId);
        studylogRepository.delete(studylog);
        memberTagService.removeMemberTag(tags, foundMember);
    }

    private void checkScrapedOrRead(Long memberId, Long studylogId) {
        if (studylogScrapRepository.existsByMemberIdAndStudylogId(memberId, studylogId)) {
            StudylogScrap studylogScrap = studylogScrapRepository.findByMemberIdAndStudylogId(memberId, studylogId)
                .orElseThrow(StudylogScrapNotExistException::new);
            studylogScrapRepository.delete(studylogScrap);
        }

        if (studylogReadRepository.existsByMemberIdAndStudylogId(memberId, studylogId)) {
            StudylogRead studylogRead = studylogReadRepository.findByMemberIdAndStudylogId(memberId, studylogId)
                .orElseThrow(StudylogReadNotExistException::new);
            studylogReadRepository.delete(studylogRead);
        }
    }

    public List<CalendarStudylogResponse> findCalendarStudylogs(String username, LocalDate localDate) {
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

    private List<Long> findReadIds(Long memberId) {
        List<StudylogRead> readList = studylogReadRepository.findByMemberId(memberId);
        return readList.stream()
            .map(StudylogRead::getStudylog)
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

    private void updateRead(List<StudylogResponse> studylogs, List<Long> readIds) {
        studylogs.forEach(studylogResponse -> {
            if (readIds.stream().anyMatch(id -> id.equals(studylogResponse.getId()))) {
                studylogResponse.setRead(true);
            }
        });
    }

    public List<StudylogRssFeedResponse> readRssFeeds(String url) {
        List<Studylog> studylogs = studylogRepository.findTop10ByOrderByCreatedAtDesc();
        return StudylogRssFeedResponse.listOf(studylogs, url);
    }
  
    public Studylog save(Studylog studylog) {
      return studylogRepository.save(studylog);
    }
}
