package wooteco.prolog.studylog.application;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_ARGUMENT;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_DOCUMENT_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_NOT_FOUND;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_SCRAP_NOT_EXIST_EXCEPTION;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.MemberTagService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.AnswerService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.session.domain.AnswerTemp;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.dto.AnswerRequest;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.application.dto.StudylogMissionRequest;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.application.dto.StudylogSessionRequest;
import wooteco.prolog.studylog.application.dto.StudylogTempResponse;
import wooteco.prolog.studylog.application.dto.StudylogWithScrapedCountResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogRead;
import wooteco.prolog.studylog.domain.StudylogScrap;
import wooteco.prolog.studylog.domain.StudylogTemp;
import wooteco.prolog.studylog.domain.Tags;
import wooteco.prolog.studylog.domain.repository.CommentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogReadRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.domain.repository.StudylogScrapRepository;
import wooteco.prolog.studylog.domain.repository.StudylogSpecification;
import wooteco.prolog.studylog.domain.repository.StudylogTempRepository;
import wooteco.prolog.studylog.domain.repository.dto.CommentCount;
import wooteco.prolog.studylog.event.StudylogDeleteEvent;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class StudylogService {

    private static final Logger logger = LoggerFactory.getLogger(StudylogService.class);

    private final MemberTagService memberTagService;
    private final DocumentService studylogDocumentService;
    private final MemberService memberService;
    private final TagService tagService;
    private final AnswerService answerService;
    private final SessionService sessionService;
    private final MissionService missionService;
    private final StudylogRepository studylogRepository;
    private final StudylogScrapRepository studylogScrapRepository;
    private final StudylogReadRepository studylogReadRepository;
    private final StudylogTempRepository studylogTempRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public List<StudylogResponse> insertStudylogs(Long memberId,
                                                  List<StudylogRequest> studylogRequests) {
        if (studylogRequests.isEmpty()) {
            throw new BadRequestException(STUDYLOG_ARGUMENT);
        }

        return studylogRequests.stream()
            .map(studylogRequest -> insertStudylog(memberId, studylogRequest))
            .collect(toList());
    }

    @Transactional
    public StudylogResponse insertStudylog(Long memberId, StudylogRequest studylogRequest) {
        Member member = memberService.findById(memberId);
        validateMemberIsCrew(member);
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Session session = sessionService.findSessionById(studylogRequest.getSessionId())
            .orElse(null);
        Mission mission = missionService.findMissionById(studylogRequest.getMissionId())
            .orElse(null);

        Studylog persistStudylog = studylogRepository.save(new Studylog(member,
            studylogRequest.getTitle(),
            studylogRequest.getContent(),
            session,
            mission,
            tags.getList())
        );

        List<Answer> answers = saveAnswers(member.getId(), studylogRequest.getAnswers(), persistStudylog);

        onStudylogCreatedEvent(member, tags, persistStudylog);
        deleteStudylogTemp(memberId);

        return StudylogResponse.of(persistStudylog, answers);
    }

    private List<Answer> saveAnswers(Long memberId, List<AnswerRequest> answers, Studylog persistStudylog) {
        if (answers == null || answers.isEmpty()) {
            return new ArrayList<>();
        }

        return answerService.saveAnswers(memberId, answers, persistStudylog);
    }

    private void validateMemberIsCrew(final Member member) {
        if (member.hasLowerImportanceRoleThan(Role.CREW)) {
            throw new BadRequestException(MEMBER_NOT_ALLOWED);
        }
    }

    @Transactional
    public StudylogTempResponse insertStudylogTemp(Long memberId, StudylogRequest studylogRequest) {
        StudylogTemp createdStudylogTemp = creteStudylogTemp(memberId, studylogRequest);

        List<AnswerTemp> answerTemps
            = answerService.saveAnswerTemp(memberId, studylogRequest.getAnswers(), createdStudylogTemp);

        return StudylogTempResponse.from(createdStudylogTemp, answerTemps);
    }

    private StudylogTemp creteStudylogTemp(Long memberId, StudylogRequest studylogRequest) {
        Member member = memberService.findById(memberId);
        Tags tags = tagService.findOrCreate(studylogRequest.getTags());
        Session session = sessionService.findSessionById(studylogRequest.getSessionId())
            .orElse(null);
        Mission mission = missionService.findMissionById(studylogRequest.getMissionId())
            .orElse(null);

        StudylogTemp requestedStudylogTemp = new StudylogTemp(member,
            studylogRequest.getTitle(),
            studylogRequest.getContent(),
            session,
            mission,
            tags.getList());

        deleteStudylogTemp(memberId);
        return studylogTempRepository.save(requestedStudylogTemp);
    }

    private void onStudylogCreatedEvent(Member foundMember, Tags tags, Studylog createdStudylog) {
        memberTagService.registerMemberTag(tags, foundMember);
        studylogDocumentService.save(createdStudylog.toStudylogDocument());
    }

    public StudylogsResponse findStudylogs(StudylogsSearchRequest request, Long memberId,
                                           boolean isAnonymousMember) {
        StudylogsResponse studylogs = findStudylogs(request, memberId);
        if (isAnonymousMember) {
            return studylogs;
        }

        List<StudylogResponse> data = studylogs.getData();
        updateScrap(data, findScrapIds(memberId));
        updateRead(data, findReadIds(memberId));
        return studylogs;
    }

    public StudylogTempResponse findStudylogTemp(Long memberId) {
        if (studylogTempRepository.existsByMemberId(memberId)) {
            StudylogTemp studylogTemp = studylogTempRepository.findByMemberId(memberId);
            List<AnswerTemp> answerTemps = answerService.findAnswersTempByMemberId(memberId);
            return StudylogTempResponse.from(studylogTemp, answerTemps);
        }
        return StudylogTempResponse.toNull();
    }

    @Transactional
    public StudylogsResponse findStudylogs(StudylogsSearchRequest request, Long memberId) {
        if (Objects.nonNull(request.getIds())) {
            Pageable pageable = request.getPageable();
            List<Long> ids = request.getIds();

            Page<Studylog> studylogs = studylogRepository.findByIdInAndDeletedFalseOrderByIdDesc(
                ids, pageable);
            Map<Long, Long> commentCounts = commentCounts(studylogs.getContent());
            return StudylogsResponse.of(studylogs, memberId, commentCounts);
        }

        if (request.getKeyword() == null || request.getKeyword().isEmpty()) {
            return findStudylogsWithoutKeyword(request.getSessions(), request.getMissions(),
                request.getTags(),
                request.getUsernames(), request.getMembers(), request.getStartDate(),
                request.getEndDate(),
                request.getPageable(), memberId);
        }

        final StudylogDocumentResponse response = studylogDocumentService.findBySearchKeyword(
            request.getKeyword(),
            request.getTags(),
            request.getMissions(),
            request.getSessions(),
            request.getUsernames(),
            request.getStartDate(),
            request.getEndDate(),
            request.getPageable()
        );

        final List<Studylog> studylogs = studylogRepository.findByIdInAndDeletedFalseOrderByIdDesc(
            response.getStudylogIds()
        );
        Map<Long, Long> commentCounts = commentCounts(studylogs);
        return StudylogsResponse.of(
            studylogs,
            response.getTotalSize(),
            response.getTotalPage(),
            response.getCurrPage(),
            memberId,
            commentCounts
        );
    }

    private Map<Long, Long> commentCounts(List<Studylog> studylogs) {
        final Map<Long, Long> commentCounts = commentRepository.countByStudylogIn(studylogs)
            .stream()
            .collect(toMap(CommentCount::getStudylogId, CommentCount::getCount));

        logger.debug("studylogs: {}", studylogs);
        logger.debug("commentCounts: {}", commentCounts);

        return studylogs.stream()
            .map(Studylog::getId)
            .collect(toMap(id -> id, id -> commentCounts.getOrDefault(id, 0L)));
    }

    public StudylogsResponse findStudylogsWithoutKeyword(
        List<Long> sessionIds,
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
            StudylogSpecification.findByDeletedFalse()
                .and(StudylogSpecification.equalIn("session", sessionIds))
                .and(StudylogSpecification.equalIn("mission", missionIds))
                .and(StudylogSpecification.findByTagIn(tagIds))
                .and(StudylogSpecification.findByUsernameIn(usernames))
                .and(StudylogSpecification.findByMemberIn(members))
                .and(StudylogSpecification.findBetweenDate(startDate, endDate))
                .and(StudylogSpecification.distinct(true))
                .and(StudylogSpecification.orderByIdDesc());

        Page<Studylog> studylogs = studylogRepository.findAll(specs, pageable);
        Map<Long, List<Answer>> answers = answerService.findAnswersByStudylogs(studylogs.getContent());
        Map<Long, Long> commentCounts = commentCounts(studylogs.getContent());
        return StudylogsResponse.of(studylogs, answers, memberId, commentCounts);
    }

    public StudylogsResponse findStudylogsOf(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return StudylogsResponse.of(
            studylogRepository.findByMember(member, pageable),
            member.getId()
        );
    }

    public Page<Studylog> findStudylogsByUsername(String username, Pageable pageable) {
        Member member = memberService.findByUsername(username);
        return studylogRepository.findByMember(member, pageable);
    }

    private void deleteStudylogTemp(Long memberId) {
        if (studylogTempRepository.existsByMemberId(memberId)) {
            studylogTempRepository.deleteByMemberId(memberId);
        }
    }

    @Transactional
    public StudylogResponse retrieveStudylogById(LoginMember loginMember, Long studylogId,
                                                 boolean isViewed) {

        Studylog studylog = findStudylogById(studylogId);

        List<Answer> answers = answerService.findAnswersByStudylogId(studylog.getId());

        onStudylogRetrieveEvent(loginMember, studylog, isViewed);

        return toStudylogResponse(loginMember, studylog, answers);
    }

    @Transactional
    public StudylogWithScrapedCountResponse retrieveStudylogByIdWithScrapedCount(
        LoginMember loginMember,
        Long studylogId, boolean isViewed) {
        Studylog studylog = findStudylogById(studylogId);

        onStudylogRetrieveEvent(loginMember, studylog, isViewed);

        return toStudylogResponseWithScrapedCount(loginMember, studylog);
    }

    private StudylogWithScrapedCountResponse toStudylogResponseWithScrapedCount(
        LoginMember loginMember,
        Studylog studylog) {
        boolean liked = studylog.likedByMember(loginMember.getId());
        boolean read = studylogReadRepository.findByMemberIdAndStudylogId(loginMember.getId(),
                studylog.getId())
            .isPresent();
        boolean scraped = studylogScrapRepository.findByMemberIdAndStudylogId(loginMember.getId(),
                studylog.getId())
            .isPresent();
        int scrapedCount = studylogScrapRepository.countByStudylogId(studylog.getId());

        return new StudylogWithScrapedCountResponse(
            StudylogResponse.of(studylog, scraped, read, liked), scrapedCount);
    }

    private void onStudylogRetrieveEvent(LoginMember loginMember, Studylog studylog,
                                         boolean isViewed) {
        // view 증가
        if (!isViewed) {
            increaseViewCount(loginMember, studylog);
        }

        if (loginMember.isAnonymous()) {
            return;
        }

        // 읽음 처리
        if (!studylogReadRepository.existsByMemberIdAndStudylogId(loginMember.getId(),
            studylog.getId())) {
            insertStudylogRead(studylog.getId(), loginMember.getId());
        }
    }

    private StudylogResponse toStudylogResponse(LoginMember loginMember, Studylog studylog, List<Answer> answers) {
        boolean liked = studylog.likedByMember(loginMember.getId());
        boolean read = studylogReadRepository.findByMemberIdAndStudylogId(loginMember.getId(), studylog.getId())
            .isPresent();
        boolean scraped = studylogScrapRepository.findByMemberIdAndStudylogId(loginMember.getId(), studylog.getId())
            .isPresent();

        return StudylogResponse.of(studylog, answers, scraped, read, liked);
    }

    public StudylogResponse findByIdAndReturnStudylogResponse(Long id) {
        return StudylogResponse.of(findStudylogById(id));
    }

    private void insertStudylogRead(Long id, Long memberId) {
        Member readMember = memberService.findById(memberId);
        Studylog readStudylog = studylogRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));
        studylogReadRepository.save(new StudylogRead(readMember, readStudylog));
    }

    public Studylog findStudylogById(Long id) {
        return studylogRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));
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
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));

        studylog.validateBelongTo(memberId);

        Session session = sessionService.findSessionById(studylogRequest.getSessionId())
            .orElse(null);
        Mission mission = missionService.findMissionById(studylogRequest.getMissionId())
            .orElse(null);

        final Member foundMember = memberService.findById(memberId);
        final Tags originalTags = tagService.findByStudylogsAndMember(studylog, foundMember);

        Tags newTags = tagService.findOrCreate(studylogRequest.getTags());
        studylog.update(studylogRequest.getTitle(), studylogRequest.getContent(), session, mission,
            newTags);

        answerService.updateAnswers(studylogRequest.getAnswers(), studylog);
        memberTagService.updateMemberTag(originalTags, newTags, foundMember);

        studylogDocumentService.update(studylog.toStudylogDocument());
    }

    @Transactional
    public void updateStudylogSession(Long memberId, Long studylogId,
                                      StudylogSessionRequest studylogSessionRequest) {
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));
        studylog.validateBelongTo(memberId);

        Session session = sessionService.findSessionById(studylogSessionRequest.getSessionId())
            .orElse(null);

        studylog.updateSession(session);
    }

    @Transactional
    public void updateStudylogMission(Long memberId, Long studylogId,
                                      StudylogMissionRequest studylogMissionRequest) {
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));
        studylog.validateBelongTo(memberId);

        Mission mission = missionService.findMissionById(studylogMissionRequest.getMissionId())
            .orElse(null);

        studylog.updateMission(mission);
    }

    @Transactional
    public void deleteStudylog(Long memberId, Long studylogId) {
        final Member foundMember = memberService.findById(memberId);
        Studylog studylog = studylogRepository.findById(studylogId)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_NOT_FOUND));
        studylog.validateBelongTo(memberId);

        final Tags tags = tagService.findByStudylogsAndMember(studylog, foundMember);
        studylogDocumentService.delete(studylog.toStudylogDocument());
        checkScrapedOrRead(memberId, studylogId);
        memberTagService.removeMemberTag(tags, foundMember);
        studylog.delete();

        eventPublisher.publishEvent(new StudylogDeleteEvent(studylogId));
    }

    private void checkScrapedOrRead(Long memberId, Long studylogId) {
        if (studylogScrapRepository.existsByMemberIdAndStudylogId(memberId, studylogId)) {
            StudylogScrap studylogScrap = studylogScrapRepository.findByMemberIdAndStudylogId(
                    memberId, studylogId)
                .orElseThrow(() -> new BadRequestException(STUDYLOG_SCRAP_NOT_EXIST_EXCEPTION));
            studylogScrapRepository.delete(studylogScrap);
        }

        if (studylogReadRepository.existsByMemberIdAndStudylogId(memberId, studylogId)) {
            StudylogRead studylogRead = studylogReadRepository.findByMemberIdAndStudylogId(memberId,
                    studylogId)
                .orElseThrow(() -> new BadRequestException(STUDYLOG_DOCUMENT_NOT_FOUND));
            studylogReadRepository.delete(studylogRead);
        }
    }

    public List<CalendarStudylogResponse> findCalendarStudylogs(String username,
                                                                LocalDate localDate) {
        final Member member = memberService.findByUsername(username);
        final LocalDateTime start = localDate.with(firstDayOfMonth()).atStartOfDay();
        final LocalDateTime end = localDate.with(lastDayOfMonth()).atTime(LocalTime.MAX);

        return studylogRepository.findByMemberBetween(member, start, end)
            .stream()
            .map(CalendarStudylogResponse::of)
            .collect(toList());
    }

    public List<Long> findScrapIds(Long memberId) {
        List<StudylogScrap> memberScraps = studylogScrapRepository.findByMemberId(memberId);
        return memberScraps.stream()
            .map(StudylogScrap::getStudylog)
            .map(Studylog::getId)
            .collect(toList());
    }

    public List<Long> findReadIds(Long memberId) {
        List<StudylogRead> readList = studylogReadRepository.findByMemberId(memberId);
        return readList.stream()
            .map(StudylogRead::getStudylog)
            .map(Studylog::getId)
            .collect(toList());
    }

    public void updateScrap(List<StudylogResponse> studylogs, List<Long> scrapIds) {
        studylogs.forEach(studylogResponse -> {
            if (scrapIds.stream().anyMatch(id -> id.equals(studylogResponse.getId()))) {
                studylogResponse.setScrap(true);
            }
        });
    }

    public void updateRead(List<StudylogResponse> studylogs, List<Long> readIds) {
        studylogs.forEach(studylogResponse -> {
            if (readIds.stream().anyMatch(id -> id.equals(studylogResponse.getId()))) {
                studylogResponse.setRead(true);
            }
        });
    }

    public List<StudylogRssFeedResponse> readRssFeeds(String url) {
        List<Studylog> studylogs = studylogRepository.findTop50ByDeletedFalseOrderByIdDesc();
        return StudylogRssFeedResponse.listOf(studylogs, url);
    }

    public Studylog save(Studylog studylog) {
        return studylogRepository.save(studylog);
    }

    public List<Studylog> findStudylogsInPeriod(Long memberId, LocalDate startDate,
                                                LocalDate endDate) {
        return studylogRepository
            .findByMemberIdAndCreatedAtBetween(memberId, LocalDateTime.of(startDate, LocalTime.MIN),
                LocalDateTime.of(endDate, LocalTime.MAX));
    }
}
