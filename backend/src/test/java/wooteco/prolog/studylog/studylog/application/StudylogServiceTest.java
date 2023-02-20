package wooteco.prolog.studylog.studylog.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.MissionRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.studylog.application.CommentService;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.CommentSaveRequest;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
import wooteco.prolog.studylog.application.dto.StudylogTempResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.application.dto.search.StudylogsSearchRequest;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
@Transactional
class StudylogServiceTest {

    private static final String TITLE1 = "이것은 제목";
    private static final String TITLE2 = "이것은 두번째 제목";
    private static final String TITLE3 = "이것은 3 제목";
    private static final String TITLE4 = "이것은 네번 제목";

    private static final String CONTENT1 = "피케이와 포코의 스터디로그";
    private static final String CONTENT2 = "피케이와 포모의 스터디로그 2";
    private static final String CONTENT3 = "피케이 스터디로그";
    private static final String CONTENT4 = "포모의 스터디로그";

    private static Tag tag1 = new Tag(1L, "소롱의글쓰기");
    private static Tag tag2 = new Tag(2L, "스프링");
    private static Tag tag3 = new Tag(3L, "감자튀기기");
    private static Tag tag4 = new Tag(4L, "집필왕웨지");
    private static Tag tag5 = new Tag(5L, "피케이");
    private static List<Tag> tags = asList(
            tag1, tag2, tag3, tag4, tag5
    );
    private static final String TAG1 = "소롱의글쓰리";
    private static final String TAG2 = "스프링";
    private static final String TAG3 = "감자튀기기";
    private static final String TAG4 = "집필왕웨지";
    private static final String TAG5 = "피케이";

    private static final String SESSION1 = "세션-1";
    private static final String SESSION2 = "세션-2";

    private static final String MISSION1 = "미션-1";
    private static final String MISSION2 = "미션-2";

    private static final String URL = "http://localhost:8080";

    private Member member1;
    private Member member2;

    private LoginMember loginMember1;
    private LoginMember loginMember2;
    private LoginMember loginMember3;

    private Session session1;
    private Session session2;

    private Mission mission1;
    private Mission mission2;

    private Studylog studylog1;
    private Studylog studylog2;
    private Studylog studylog3;
    private Studylog studylog4;

    @Autowired
    private StudylogService studylogService;

    @Autowired
    private StudylogScrapService studylogScrapService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DocumentService studylogDocumentService;
    @Autowired
    private AbilityService abilityService;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        SessionResponse sessionResponse1 = sessionService.create(new SessionRequest("세션1"));
        SessionResponse sessionResponse2 = sessionService.create(new SessionRequest("세션2"));

        this.session1 = new Session(sessionResponse1.getId(), null, sessionResponse1.getName());
        this.session2 = new Session(sessionResponse2.getId(), null, sessionResponse2.getName());

        MissionResponse missionResponse1 = missionService
                .create(new MissionRequest("자동차 미션", session1.getId()));
        MissionResponse missionResponse2 = missionService
                .create(new MissionRequest("수동차 미션", session2.getId()));

        this.mission1 = new Mission(missionResponse1.getId(), missionResponse1.getName(), session1);
        this.mission2 = new Mission(missionResponse2.getId(), missionResponse2.getName(), session1);

        this.member1 = memberService.findOrCreateMember(new GithubProfileResponse("이름1", "별명1", "1", "image"));
        this.member2 = memberService.findOrCreateMember(new GithubProfileResponse("이름2", "별명2", "2", "image"));

        this.loginMember1 = new LoginMember(member1.getId(), Authority.MEMBER);
        this.loginMember2 = new LoginMember(member2.getId(), Authority.MEMBER);
        this.loginMember3 = new LoginMember(null, Authority.ANONYMOUS);

        this.studylog1 = new Studylog(member1,
                TITLE1, "피케이와 포모의 스터디로그", session1, mission1,
                asList(tag1, tag2));
        this.studylog2 = new Studylog(member1,
                TITLE2, "피케이와 포모의 스터디로그 2", session1, mission1,
                asList(tag2, tag3));
        this.studylog3 = new Studylog(member2,
                TITLE3, "피케이 스터디로그", session1, mission2,
                asList(tag3, tag4, tag5));
        this.studylog4 = new Studylog(member2, TITLE4, "포모의 스터디로그", session1, mission2, emptyList());
    }

    @DisplayName("스터디로그를 삽입한다. - 삽입 시 studylogDocument도 삽입된다.")
    @Test
    void insert() {
        // arrange
        final Member member = createMember(1);
        final MissionResponse mission = createMission("세션", "미션");

        // act
        final StudylogResponse studylog = createStudyLog(
                member.getId(), mission, TITLE1, CONTENT1, Arrays.asList(TAG1, TAG2)
        );

        // assert
        StudylogDocument studylogDocument = studylogDocumentService.findById(studylog.getId());
        assertAll(
                () -> assertThat(studylogDocument.getId()).isEqualTo((long) studylog.getId()),
                () -> assertThat(studylogDocument.getTitle()).isEqualTo(TITLE1),
                () -> assertThat(studylogDocument.getContent()).isEqualTo(CONTENT1)
        );
    }

    @DisplayName("스터디로그 여러개 삽입")
    @Test
    void insertStudylogsTest() {
        // arrange
        final MissionResponse mission = createMission("세션", "미션");

        final Member member1 = createMember(1);
        final List<String> tagNames1 = asList(TAG1, TAG2);

        final Member member2 = createMember(2);
        final List<String> tagNames2 = asList(TAG1, TAG3);

        // act
        final StudylogResponse studylog1 = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1, tagNames1);
        final StudylogResponse studylog2 = createStudyLog(member2.getId(), mission, TITLE2, CONTENT2, tagNames2);

        // assert
        assertStudylog(studylog1, member1, mission, TITLE1, CONTENT1, tagNames1);
        assertStudylog(studylog2, member2, mission, TITLE2, CONTENT2, tagNames2);
    }

    @DisplayName("검색 및 필터")
    @ParameterizedTest
    @MethodSource("findWithFilter")
    void findWithFilter(String keyword, List<String> sessionNames, List<String> missionNames,
                        List<String> tagNames, List<String> expectedTitles) {
        // arrange
        final MissionResponse mission1 = createMission(SESSION1, MISSION1);
        final MissionResponse mission2 = createMission(SESSION2, MISSION2);

        final Member member1 = createMember(1);
        final Member member2 = createMember(2);

        final StudylogResponse studyLog1 = createStudyLog(member1.getId(), mission1, TITLE1, CONTENT1,
                asList(TAG1, TAG2));
        final StudylogResponse studyLog2 = createStudyLog(member1.getId(), mission1, TITLE2, CONTENT2,
                asList(TAG2, TAG3));
        final StudylogResponse studyLog3 = createStudyLog(member2.getId(), mission2, TITLE3, CONTENT3,
                asList(TAG3, TAG4, TAG5));
        final StudylogResponse studyLog4 = createStudyLog(member2.getId(), mission2, TITLE4, CONTENT4, emptyList());

        final Map<String, StudylogResponse> studylogResponses = Stream.of(studyLog1, studyLog2, studyLog3, studyLog4)
                .collect(toMap(StudylogResponse::getTitle, r -> r));

        // act
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                new StudylogsSearchRequest(
                        keyword, sessionIds(sessionNames), missionIds(missionNames),
                        tagIds(tagNames), emptyList(), emptyList(),
                        LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                        LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                        null, PageRequest.of(0, 10)
                ), null
        );

        // assert
        List<StudylogResponse> expectedResponses = expectedTitles.stream()
                .map(studylogResponses::get)
                .collect(toList());

        assertThat(studylogsResponse.getData()).extracting(StudylogResponse::getTitle)
                .containsExactlyElementsOf(expectedResponses.stream().map(
                        StudylogResponse::getTitle).collect(toList()));
    }

    private static Stream<Arguments> findWithFilter() {
        return Stream.of(
                Arguments.of(null, emptyList(), emptyList(), Arrays.asList(TAG1, TAG2, TAG3),
                        Arrays.asList(TITLE3, TITLE2, TITLE1)),
                Arguments.of(null, emptyList(), emptyList(), singletonList(TAG2), Arrays.asList(TITLE2, TITLE1)),
                Arguments.of("", emptyList(), emptyList(), singletonList(TAG3), Arrays.asList(TITLE3, TITLE2)),
                Arguments.of("", emptyList(), singletonList(MISSION1), emptyList(), asList(TITLE2, TITLE1)),
                Arguments.of("", emptyList(), singletonList(MISSION2), emptyList(), asList(TITLE4, TITLE3)),
                Arguments.of("", emptyList(), singletonList(MISSION1), singletonList(TAG1), singletonList(TITLE1)),
                Arguments.of("", singletonList(SESSION1), singletonList(MISSION1), Arrays.asList(TAG1, TAG2, TAG3),
                        asList(TITLE2, TITLE1)),
                Arguments.of("", emptyList(), singletonList(MISSION1), singletonList(TAG2), asList(TITLE2, TITLE1)),
                Arguments.of("", emptyList(), Arrays.asList(MISSION1, MISSION2), singletonList(TAG3),
                        asList(TITLE3, TITLE2)),
                Arguments.of("", emptyList(), emptyList(), emptyList(), asList(TITLE4, TITLE3, TITLE2, TITLE1)),
                Arguments.of("이것은 제목", emptyList(), emptyList(), emptyList(), asList(TITLE4, TITLE3, TITLE2, TITLE1)),
                Arguments.of("궁둥이", emptyList(), emptyList(), emptyList(), emptyList())
        );
    }

    private List<Long> sessionIds(List<String> sessionNames) {
        return sessionNames.stream()
                .map(sessionRepository::findByName)
                .map(Optional::get)
                .map(Session::getId)
                .collect(toList());
    }

    private List<Long> missionIds(List<String> missionNames) {
        return missionNames.stream()
                .map(missionRepository::findByName)
                .map(Optional::get)
                .map(Mission::getId)
                .collect(Collectors.toList());
    }

    private List<Long> tagIds(List<String> tagnNames) {
        final List<TagRequest> requests = tagnNames.stream()
                .map(TagRequest::new)
                .collect(toList());

        return tagService.findOrCreate(requests).getList()
                .stream()
                .map(Tag::getId)
                .collect(toList());
    }

    @DisplayName("스크랩한 경우 scrap이 true로 응답된다.")
    @Test
    void findStudylogsWithScrapData() {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);

        final Member member1 = createMember(1);
        final Member member2 = createMember(2);

        final StudylogResponse studyLog1 = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1, emptyList());
        final StudylogResponse studyLog2 = createStudyLog(member2.getId(), mission, TITLE2, CONTENT2, emptyList());

        // act
        studylogScrapService.registerScrap(member1.getId(), studyLog2.getId());

        // assert
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                new StudylogsSearchRequest(
                        "",
                        emptyList(),
                        missionIds(singletonList(MISSION1)),
                        emptyList(),
                        emptyList(),
                        emptyList(),
                        LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                        LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                        null,
                        PageRequest.of(0, 10)
                ), member1.getId(), member1.isAnonymous()
        );

        final StudylogResponse studylogResponse1 = studylogsResponse.getData().stream()
                .filter(log -> log.getId().equals(studyLog1.getId())).findAny().get();
        final StudylogResponse studylogResponse2 = studylogsResponse.getData().stream()
                .filter(log -> log.getId().equals(studyLog2.getId())).findAny().get();

        assertThat(studylogResponse1.isScrap()).isFalse();
        assertThat(studylogResponse2.isScrap()).isTrue();
    }

    @DisplayName("스터디로그 조회 시 조회수가 오른다.")
    @Test
    void findByIdTest() {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member1 = createMember(1);
        final Member member2 = createMember(2);
        final StudylogResponse studylogResponse = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1,
                emptyList());

        // act
        final LoginMember loginMember1 = new LoginMember(member2.getId(), Authority.MEMBER);
        final LoginMember loginMember2 = new LoginMember(null, Authority.ANONYMOUS);
        studylogService.retrieveStudylogById(loginMember1, studylogResponse.getId(), false);
        studylogService.retrieveStudylogById(loginMember2, studylogResponse.getId(), false);

        // assert
        final Studylog studylog = studylogService.findStudylogById(studylogResponse.getId());
        assertThat(studylog.getViewCount()).isEqualTo(2);
    }

    @DisplayName("자신이 작성한 스터디로그 조회 시 조회수가 오르지 않는다.")
    @Test
    void findByIdSameUserTest() {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member = createMember(1);
        final StudylogResponse studylogResponse = createStudyLog(member.getId(), mission, TITLE1, CONTENT1,
                emptyList());

        // act
        final LoginMember loginMember = new LoginMember(member.getId(), Authority.MEMBER);
        studylogService.retrieveStudylogById(loginMember, studylogResponse.getId(), false);

        // assert
        final Studylog studylog = studylogService.findStudylogById(studylogResponse.getId());
        assertThat(studylog.getViewCount()).isEqualTo(0);
    }

    @DisplayName("유저 이름으로 스터디로그를 조회한다.")
    @Test
    void findStudylogsOfTest() {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member1 = createMember(1);
        final Member member2 = createMember(2);

        createStudyLog(member1.getId(), mission, TITLE1, CONTENT1, emptyList());
        createStudyLog(member2.getId(), mission, TITLE2, CONTENT2, emptyList());

        // act
        final StudylogsResponse studylogsResponse = studylogService
                .findStudylogsOf(member1.getUsername(), Pageable.unpaged());

        // assert
        assertThat(studylogsResponse.getData()).hasSize(1);
        assertStudylog(studylogsResponse.getData().get(0), member1, mission, TITLE1, CONTENT1, emptyList());
    }

    @DisplayName("유저 이름으로 스터디로그를 조회한다 - 페이징")
    @ParameterizedTest
    @MethodSource("findStudylogsOfPagingTest")
    void findStudylogsOfPagingTest(PageRequest pageRequest, int expectedSize) {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member = createMember(1);

        for (int i = 0; i < 50; i++) {
            createStudyLog(member.getId(), mission, TITLE1, CONTENT1, emptyList());
        }

        // act
        StudylogsResponse studylogsResponse = studylogService
                .findStudylogsOf(member.getUsername(), pageRequest);

        // assert
        assertThat(studylogsResponse.getData().size()).isEqualTo(expectedSize);
    }

    private static Stream<Arguments> findStudylogsOfPagingTest() {
        return Stream.of(
                Arguments.of(PageRequest.of(0, 10), 10),
                Arguments.of(PageRequest.of(0, 20), 20),
                Arguments.of(PageRequest.of(0, 60), 50),
                Arguments.of(PageRequest.of(3, 15), 5),
                Arguments.of(PageRequest.of(1, 50), 0),
                Arguments.of(PageRequest.of(4, 11), 6)
        );
    }

    @DisplayName("스터디로그를 수정한다.")
    @Test
    void updateStudylogTest() {
        // arrange
        final MissionResponse mission1 = createMission(SESSION1, MISSION1);
        final MissionResponse mission2 = createMission(SESSION2, MISSION2);
        final Member member = createMember(1);
        final StudylogResponse studylogResponse = createStudyLog(member.getId(), mission1, TITLE1, CONTENT1,
                emptyList());

        final List<String> updateTagNames = Arrays.asList(TAG1, TAG2, TAG3, TAG4, TAG5);
        final List<TagRequest> updateTagRequests = updateTagNames.stream()
                .map(TagRequest::new)
                .collect(toList());
        final String updateTitle = "updateTitle";
        final String updateContent = "updateContent";

        StudylogRequest updatingRequest = new StudylogRequest(
                updateTitle, updateContent, mission2.getId(), updateTagRequests
        );

        // act
        studylogService.updateStudylog(member.getId(), studylogResponse.getId(), updatingRequest);

        // assert
        StudylogResponse actualResponse = studylogService.findByIdAndReturnStudylogResponse(studylogResponse.getId());
        StudylogDocument actualDocument = studylogDocumentService.findById(studylogResponse.getId());
        assertAll(
                () -> assertStudylog(actualResponse, member, mission2, updateTitle, updateContent, updateTagNames),
                () -> assertThat(actualDocument.getTitle()).isEqualTo(updateTitle),
                () -> assertThat(actualDocument.getContent()).isEqualTo(updateContent)
        );
    }

    @DisplayName("스터디로그를 삭제한다.")
    @Test
    void deleteStudylogTest() {
        // arrange
        final MissionResponse mission1 = createMission(SESSION1, MISSION1);
        final Member member = createMember(1);
        final StudylogResponse studylogResponse = createStudyLog(member.getId(), mission1, TITLE1, CONTENT1,
                emptyList());

        // act
        studylogService.deleteStudylog(member.getId(), studylogResponse.getId());

        // assert
        Studylog deletedStudylog = studylogService.findStudylogById(studylogResponse.getId());
        assertThat(deletedStudylog.isDeleted()).isTrue();
        assertThatThrownBy(() -> studylogDocumentService.findById(studylogResponse.getId()))
                .isInstanceOf(StudylogDocumentNotFoundException.class);
    }

    @Test
    @DisplayName("캘린더 스터디로그 조회 기능")
    void calendarStudylogTest() throws Exception {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member = createMember(1);

        final StudylogResponse studyLog1 = createStudyLog(member.getId(), mission, TITLE1, CONTENT1,
                emptyList());
        final StudylogResponse studyLog2 = createStudyLog(member.getId(), mission, TITLE2, CONTENT2,
                emptyList());
        final StudylogResponse studyLog3 = createStudyLog(member.getId(), mission, TITLE3, CONTENT3,
                emptyList());

        // act
        final List<CalendarStudylogResponse> calendarPosts =
                studylogService.findCalendarStudylogs(member.getUsername(), LocalDate.now());

        // assert
        assertThat(calendarPosts)
                .extracting(CalendarStudylogResponse::getTitle)
                .containsExactlyInAnyOrder(studyLog1.getTitle(), studyLog2.getTitle(), studyLog3.getTitle());
    }

    @DisplayName("RSS 피드를 조회한다.")
    @Test
    void readRssFeeds() {
        // arrange
        final MissionResponse mission = createMission(SESSION1, MISSION1);
        final Member member1 = createMember(1);
        final Member member2 = createMember(2);

        final StudylogResponse studyLog1 = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1,
                emptyList());
        final StudylogResponse studyLog2 = createStudyLog(member1.getId(), mission, TITLE2, CONTENT2,
                emptyList());
        final StudylogResponse studyLog3 = createStudyLog(member2.getId(), mission, TITLE3, CONTENT3,
                emptyList());

        // act
        List<StudylogRssFeedResponse> actual = studylogService.readRssFeeds(URL);

        // assert
        List<String> expectedLinkUrl = Stream.of(studyLog1, studyLog2, studyLog3)
                .map(StudylogResponse::getId)
                .map(id -> URL + "/studylogs/" + id)
                .collect(toList());

        assertThat(actual)
                .extracting(StudylogRssFeedResponse::getLink)
                .containsExactlyInAnyOrderElementsOf(expectedLinkUrl);
    }

    @DisplayName("임시저장된 스터디로그를 조회한다.")
    @Test
    void findStudylogTemp() {
        //given
        Mission mission = missionRepository.save(mission1);
        String title = "임시저장 제목";
        String content = "임시저장 내용";
        StudylogRequest studylogRequest = new StudylogRequest(title, content,
                mission.getId(),
                toTagRequests(tags));
        studylogService.insertStudylogTemp(member1.getId(),
                studylogRequest);

        //when
        final StudylogTempResponse studylogTempResponse = studylogService.findStudylogTemp(member1.getId());

        //then
        assertThat(studylogTempResponse.getTitle()).isEqualTo(title);
        assertThat(studylogTempResponse.getContent()).isEqualTo(content);
        assertThat(studylogTempResponse.getAbilities()).isEmpty();
    }

    @DisplayName("학습로그 목록조회 시 댓글 갯수도 같이 조회한다.")
    @ParameterizedTest
    @MethodSource("provideFilterForGetCommentCount")
    void getCommentCount(final String keyword) {
        // arrange
        final Member member1 = createMember(1);
        final Member member2 = createMember(2);
        final MissionResponse mission = createMission(SESSION1, MISSION1);

        final StudylogResponse studyLog1 = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1, emptyList());
        commentService.insertComment(new CommentSaveRequest(member1.getId(), studyLog1.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog1.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog1.getId(), "내용"));

        final StudylogResponse studyLog2 = createStudyLog(member1.getId(), mission, TITLE2, CONTENT2, emptyList());
        commentService.insertComment(new CommentSaveRequest(member1.getId(), studyLog2.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog2.getId(), "내용"));

        createStudyLog(member1.getId(), mission, TITLE3, CONTENT3, emptyList());

        // act
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                new StudylogsSearchRequest(
                        keyword, emptyList(), emptyList(),
                        emptyList(), emptyList(), emptyList(),
                        LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                        LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                        null, PageRequest.of(0, 10)
                ), null
        );

        // assert
        List<Long> commentCounts = studylogsResponse.getData().stream()
                .map(StudylogResponse::getCommentCount)
                .collect(toList());
        assertThat(commentCounts).containsExactly(0L, 2L, 3L);
    }

    private static Stream<Arguments> provideFilterForGetCommentCount() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of("제목")
        );
    }

    @DisplayName("학습로그 Id로 목록조회 시 댓글 갯수도 같이 조회한다.")
    @Test
    void getCommentCountByStudylogIds() {
        // arrange
        final Member member1 = createMember(1);
        final Member member2 = createMember(2);
        final MissionResponse mission = createMission(SESSION1, MISSION1);

        final StudylogResponse studyLog1 = createStudyLog(member1.getId(), mission, TITLE1, CONTENT1, emptyList());
        commentService.insertComment(new CommentSaveRequest(member1.getId(), studyLog1.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog1.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog1.getId(), "내용"));

        final StudylogResponse studyLog2 = createStudyLog(member1.getId(), mission, TITLE2, CONTENT2, emptyList());
        commentService.insertComment(new CommentSaveRequest(member1.getId(), studyLog2.getId(), "내용"));
        commentService.insertComment(new CommentSaveRequest(member2.getId(), studyLog2.getId(), "내용"));

        final StudylogResponse studyLog3 = createStudyLog(member1.getId(), mission, TITLE3, CONTENT3, emptyList());

        // act
        final List<Long> studyLogIds = asList(studyLog1.getId(), studyLog2.getId(), studyLog3.getId());
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
                new StudylogsSearchRequest(
                        "", emptyList(), emptyList(),
                        emptyList(), emptyList(), emptyList(),
                        LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                        LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                        studyLogIds, PageRequest.of(0, 10)
                ), null
        );

        // assert
        List<Long> commentCounts = studylogsResponse.getData().stream()
                .map(StudylogResponse::getCommentCount)
                .collect(toList());
        assertThat(commentCounts).containsExactly(0L, 2L, 3L);
    }

    private void assertStudylog(final StudylogResponse studylog, final Member member, final MissionResponse mission,
                                final String title, final String content, final List<String> tagNames) {
        assertThat(studylog.getAuthor().getNickname()).isEqualTo(member.getNickname());
        assertThat(studylog.getMission().getId()).isEqualTo(mission.getId());
        assertThat(studylog.getTitle()).isEqualTo(title);
        assertThat(studylog.getContent()).isEqualTo(content);
        assertThat(studylog.getTags())
                .extracting(TagResponse::getName)
                .containsExactlyInAnyOrderElementsOf(tagNames);
    }

    private StudylogResponse createStudyLog(
            final Long memberId, final MissionResponse mission, final String title,
            final String content, final List<String> tagNames
    ) {
        List<TagRequest> tagRequests = tagNames.stream()
                .map(TagRequest::new)
                .collect(toList());

        StudylogRequest studylogRequest = new StudylogRequest(
                title, content, mission.getSession().getId(), mission.getId(), tagRequests
        );

        return studylogService.insertStudylog(memberId, studylogRequest);
    }

    private Member createMember(int githubId) {
        return memberService.findOrCreateMember(
                new GithubProfileResponse("name" + githubId, "name" + githubId, String.valueOf(githubId), "image"));
    }

    private MissionResponse createMission(String sessionName, String missionName) {
        SessionResponse session = sessionService.create(new SessionRequest(sessionName));
        return missionService.create(new MissionRequest(missionName, session.getId()));
    }

    public List<StudylogResponse> insertStudylogs(Member member, Studylog... studylogs) {
        return insertStudylogs(member, asList(studylogs));
    }

    private List<StudylogResponse> insertStudylogs(Member member, List<Studylog> studylogs) {
        List<StudylogRequest> studylogRequests = studylogs.stream()
                .map(studylog ->
                        new StudylogRequest(
                                studylog.getTitle(),
                                studylog.getContent(),
                                studylog.getSession().getId(),
                                studylog.getMission().getId(),
                                toTagRequests(studylog)
                        )
                )
                .collect(toList());

        return studylogService.insertStudylogs(member.getId(), studylogRequests);
    }

    private List<TagRequest> toTagRequests(List<Tag> tags) {
        return tags.stream()
                .map(tag -> new TagRequest(tag.getName()))
                .collect(toList());
    }

    private List<TagRequest> toTagRequests(Studylog studylog) {
        return studylog.getStudylogTags().stream()
                .map(studylogTag -> new TagRequest(studylogTag.getTag().getName()))
                .collect(toList());
    }

    private List<Long> 역량을_저장한다() {
        AbilityCreateRequest parentRequest1 = new AbilityCreateRequest("부모1", "부모설명1", "1", null);
        AbilityCreateRequest parentRequest2 = new AbilityCreateRequest("부모2", "부모설명2", "2", null);
        Long abilityId1 = abilityService.createAbility(member1.getId(), parentRequest1).getId();
        Long abilityId2 = abilityService.createAbility(member1.getId(), parentRequest2).getId();
        return asList(abilityId1, abilityId2);
    }
}
