package wooteco.prolog.studylog.studylog.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
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
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.CalendarStudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogRssFeedResponse;
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

    private static final String STUDYLOG1_TITLE = "????????? ??????";
    private static final String STUDYLOG2_TITLE = "????????? ????????? ??????";
    private static final String STUDYLOG3_TITLE = "????????? 3 ??????";
    private static final String STUDYLOG4_TITLE = "????????? ?????? ??????";

    private static Tag tag1 = new Tag(1L, "??????????????????");
    private static Tag tag2 = new Tag(2L, "?????????");
    private static Tag tag3 = new Tag(3L, "???????????????");
    private static Tag tag4 = new Tag(4L, "???????????????");
    private static Tag tag5 = new Tag(5L, "?????????");
    private static List<Tag> tags = asList(
        tag1, tag2, tag3, tag4, tag5
    );

    private static final String URL = "http://localhost:8080";

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

    private static Stream<Arguments> findWithFilter() {
        return Stream.of(
            Arguments.of(null, emptyList(), emptyList(),
                         asList(tag1.getId(), tag2.getId(), tag3.getId()), asList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
            Arguments.of(null, emptyList(), emptyList(), singletonList(tag2.getId()), emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
            Arguments.of("", emptyList(), emptyList(), singletonList(tag3.getId()), emptyList(),
                         asList(STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
            Arguments.of("", emptyList(), singletonList(1L), emptyList(), emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
            Arguments.of("", emptyList(), singletonList(2L), emptyList(), asList(),
                         asList(STUDYLOG3_TITLE, STUDYLOG4_TITLE)),
            Arguments.of("", emptyList(), singletonList(1L), singletonList(tag1.getId()),
                         emptyList(),
                         singletonList(STUDYLOG1_TITLE)),
            Arguments.of("", singletonList(1L), singletonList(1L),
                         asList(tag1.getId(), tag2.getId(), tag3.getId()), emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
            Arguments.of("", emptyList(), singletonList(1L), singletonList(tag2.getId()),
                         emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
            Arguments.of("", emptyList(), asList(1L, 2L), singletonList(tag3.getId()), emptyList(),
                         asList(STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
            Arguments.of("", emptyList(), emptyList(), emptyList(), emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE,
                                STUDYLOG4_TITLE)),
            Arguments.of("????????? ??????", emptyList(), emptyList(), emptyList(), emptyList(),
                         asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE,
                                STUDYLOG4_TITLE)),
            Arguments.of("?????????", emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        );
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

    @BeforeEach
    void setUp() {
        SessionResponse sessionResponse1 = sessionService.create(new SessionRequest("??????1"));
        SessionResponse sessionResponse2 = sessionService.create(new SessionRequest("??????2"));

        this.session1 = new Session(sessionResponse1.getId(), sessionResponse1.getName());
        this.session2 = new Session(sessionResponse2.getId(), sessionResponse2.getName());

        MissionResponse missionResponse1 = missionService
            .create(new MissionRequest("????????? ??????", session1.getId()));
        MissionResponse missionResponse2 = missionService
            .create(new MissionRequest("????????? ??????", session2.getId()));

        this.mission1 = new Mission(missionResponse1.getId(), missionResponse1.getName(), session1);
        this.mission2 = new Mission(missionResponse2.getId(), missionResponse2.getName(), session1);

        this.member1 = memberService.findOrCreateMember(new GithubProfileResponse("??????1", "??????1", "1", "image"));
        this.member2 = memberService.findOrCreateMember(new GithubProfileResponse("??????2", "??????2", "2", "image"));

        this.loginMember1 = new LoginMember(member1.getId(), Authority.MEMBER);
        this.loginMember2 = new LoginMember(member2.getId(), Authority.MEMBER);
        this.loginMember3 = new LoginMember(null, Authority.ANONYMOUS);

        this.studylog1 = new Studylog(member1,
                                      STUDYLOG1_TITLE, "???????????? ????????? ???????????????", session1, mission1,
                                      asList(tag1, tag2));
        this.studylog2 = new Studylog(member1,
                                      STUDYLOG2_TITLE, "???????????? ????????? ??????????????? 2", session1, mission1,
                                      asList(tag2, tag3));
        this.studylog3 = new Studylog(member2,
                                      STUDYLOG3_TITLE, "????????? ???????????????", session1, mission2,
                                      asList(tag3, tag4, tag5));
        this.studylog4 = new Studylog(member2, STUDYLOG4_TITLE, "????????? ???????????????", session1, mission2, emptyList());
    }

    @DisplayName("?????????????????? ????????????. - ?????? ??? studylogDocument??? ????????????.")
    @Test
    void insert() {
        // given
        List<StudylogResponse> studylogResponses = insertStudylogs(member1, studylog1);
        Long id = studylogResponses.get(0).getId();

        // when
        StudylogDocument studylogDocument = studylogDocumentService.findById(id);
        // then
        assertAll(
            () -> assertThat(studylogDocument.getId()).isEqualTo(id),
            () -> assertThat(studylogDocument.getTitle()).isEqualTo(studylog1.getTitle()),
            () -> assertThat(studylogDocument.getContent()).isEqualTo(studylog1.getContent())
        );
    }

    @DisplayName("??????????????? ????????? ??????")
    @Test
    void insertStudylogsTest() {
        //given
        List<StudylogResponse> studylogsOfMember1 = insertStudylogs(member1, studylog1, studylog2);
        List<StudylogResponse> studylogsOfMember2 = insertStudylogs(member2, studylog3, studylog4);
        //when
        //then
        List<String> titles = Stream
            .concat(studylogsOfMember1.stream(), studylogsOfMember2.stream())
            .map(StudylogResponse::getTitle)
            .collect(toList());

        assertThat(titles).contains(
            studylog1.getTitle(),
            studylog2.getTitle(),
            studylog3.getTitle(),
            studylog4.getTitle()
        );

        List<String> members = Stream
            .concat(studylogsOfMember1.stream(), studylogsOfMember2.stream())
            .map(StudylogResponse::getAuthor)
            .map(MemberResponse::getNickname)
            .collect(toList());

        assertThat(members).contains(member1.getNickname(), member2.getNickname());
    }

    @DisplayName("?????? ??? ??????")
    @ParameterizedTest
    @MethodSource("findWithFilter")
    void findWithFilter(String keyword, List<Long> sessionIds, List<Long> missionIds,
                        List<Long> tagIds, List<String> usernames,
                        List<String> expectedStudylogTitles) {
        //given
        insertStudylogs(member1, studylog1, studylog2);
        insertStudylogs(member2, studylog3, studylog4);

        // document ????????? ?????????...
        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
            new StudylogsSearchRequest(
                keyword,
                sessionIds,
                missionIds,
                tagIds,
                usernames,
                new ArrayList<>(),
                LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                null,
                PageRequest.of(0, 10)
            ), null
        );

        //then
        List<String> titles = studylogsResponse.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(toList());

        assertThat(titles).containsExactlyInAnyOrderElementsOf(
            expectedStudylogTitles
        );
    }

    @DisplayName("???????????? ?????? scrap??? true??? ????????????.")
    @ParameterizedTest
    @MethodSource("findWithFilter")
    void findStudylogsWithScrapData(String keyword, List<Long> sessionIds, List<Long> missionIds,
                                    List<Long> tagIds, List<String> usernames,
                                    List<String> expectedStudylogTitles) {
        //given
        insertStudylogs(member1, studylog1, studylog2);
        List<StudylogResponse> studylogResponses = insertStudylogs(member2, studylog3, studylog4);
        StudylogResponse studylog3Response = studylogResponses.get(0);
        StudylogResponse studylog4Response = studylogResponses.get(1);

        studylogScrapService.registerScrap(member1.getId(), studylog3Response.getId());
        studylogScrapService.registerScrap(member1.getId(), studylog4Response.getId());

        StudylogsResponse studylogsResponse = studylogService.findStudylogs(
            new StudylogsSearchRequest(
                keyword,
                sessionIds,
                missionIds,
                tagIds,
                usernames,
                new ArrayList<>(),
                LocalDate.parse("19990106", DateTimeFormatter.BASIC_ISO_DATE),
                LocalDate.parse("99991231", DateTimeFormatter.BASIC_ISO_DATE),
                null,
                PageRequest.of(0, 10)
            ), member1.getId(), member1.isAnonymous()
        );

        //then
        List<Boolean> scraps = studylogsResponse.getData().stream()
            .filter(studylogResponse ->
                studylogResponse.getId().equals(studylog3Response.getId()) ||
                studylogResponse.getId().equals(studylog4Response.getId())
            )
            .map(StudylogResponse::isScrap)
            .collect(toList());

        assertThat(scraps).doesNotContain(false);
    }

    @DisplayName("??????????????? ?????? ??? ???????????? ?????????.")
    @Test
    void findByIdTest() {
        List<StudylogResponse> studylogResponses = insertStudylogs(member1, studylog1, studylog2);
        StudylogResponse targetStudylog = studylogResponses.get(0);

        StudylogResponse studylogResponse = studylogService.retrieveStudylogById(loginMember2, targetStudylog.getId(), false);

        assertThat(studylogResponse.getViewCount()).isEqualTo(1);

        studylogResponse = studylogService.retrieveStudylogById(loginMember3, targetStudylog.getId(), false);

        assertThat(studylogResponse.getViewCount()).isEqualTo(2);
    }

    @DisplayName("????????? ????????? ??????????????? ?????? ??? ???????????? ????????? ?????????.")
    @Test
    void findByIdSameUserTest() {
        List<StudylogResponse> studylogResponses = insertStudylogs(member1, studylog1, studylog2);
        StudylogResponse targetStudylog = studylogResponses.get(0);

        StudylogResponse studylogResponse = studylogService.retrieveStudylogById(loginMember1, targetStudylog.getId(), false);

        assertThat(studylogResponse.getViewCount()).isEqualTo(0);
    }

    @DisplayName("?????? ???????????? ?????????????????? ????????????.")
    @Test
    void findStudylogsOfTest() {
        insertStudylogs(member1, studylog1, studylog2);
        insertStudylogs(member2, studylog3, studylog4);

        StudylogsResponse studylogsResponseOfMember1 = studylogService
            .findStudylogsOf(member1.getUsername(), Pageable.unpaged());
        StudylogsResponse studylogsResponseOfMember2 = studylogService
            .findStudylogsOf(member2.getUsername(), Pageable.unpaged());

        List<String> expectedResultOfMember1 = studylogsResponseOfMember1.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(toList());
        List<String> expectedResultOfMember2 = studylogsResponseOfMember2.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(toList());

        assertThat(expectedResultOfMember1)
            .containsExactly(studylog1.getTitle(), studylog2.getTitle());
        assertThat(expectedResultOfMember2)
            .containsExactly(studylog3.getTitle(), studylog4.getTitle());
    }

    @DisplayName("?????? ???????????? ?????????????????? ???????????? - ?????????")
    @ParameterizedTest
    @MethodSource("findStudylogsOfPagingTest")
    void findStudylogsOfPagingTest(PageRequest pageRequest, int expectedSize) {
        List<Studylog> largeStudylogs = IntStream.range(0, 50)
            .mapToObj(it -> studylog1)
            .collect(toList());

        insertStudylogs(member1, largeStudylogs);

        StudylogsResponse studylogsResponseOfMember1 = studylogService
            .findStudylogsOf(member1.getUsername(), pageRequest);

        assertThat(studylogsResponseOfMember1.getData().size()).isEqualTo(expectedSize);
    }

    @DisplayName("?????????????????? ????????????.")
    @Test
    void updateStudylogTest() {
        //given
        List<StudylogResponse> studylogRespons = insertStudylogs(member1, studylog1);
        StudylogResponse targetStudylog = studylogRespons.get(0);
        StudylogRequest updateStudylogRequest = new StudylogRequest("updateTitle", "updateContent",
                                                                    2L,
                                                                    toTagRequests(tags));

        //when
        studylogService.updateStudylog(member1.getId(), targetStudylog.getId(), updateStudylogRequest);

        //then
        StudylogResponse expectedResult = studylogService.findByIdAndReturnStudylogResponse(targetStudylog.getId());
        List<String> updateTagNames = tags.stream()
            .map(Tag::getName)
            .collect(toList());

        List<String> expectedTagNames = expectedResult.getTags().stream()
            .map(TagResponse::getName)
            .collect(toList());

        assertThat(expectedResult.getTitle()).isEqualTo(updateStudylogRequest.getTitle());
        assertThat(expectedResult.getContent()).isEqualTo(updateStudylogRequest.getContent());
        assertThat(expectedResult.getMission().getId())
            .isEqualTo(updateStudylogRequest.getMissionId());
        assertThat(expectedTagNames).isEqualTo(updateTagNames);
    }

    @DisplayName("?????????????????? ???????????? - ?????? ??? studylogDocument??? ????????????.")
    @Test
    void update() {
        // given
        List<StudylogResponse> studylogResponses = insertStudylogs(member1, studylog1);
        Long id = studylogResponses.get(0).getId();
        StudylogRequest updateStudylogRequest = new StudylogRequest("updateTitle", "updateContent",
                                                                    2L,
                                                                    toTagRequests(tags));

        studylogService.updateStudylog(member1.getId(), id, updateStudylogRequest);

        // when
        StudylogDocument studylogDocument = studylogDocumentService.findById(id);

        // then
        assertAll(
            () -> assertThat(studylogDocument.getId()).isEqualTo(id),
            () -> assertThat(studylogDocument.getTitle()).isEqualTo(updateStudylogRequest.getTitle()),
            () -> assertThat(studylogDocument.getContent()).isEqualTo(updateStudylogRequest.getContent())
        );
    }

    @DisplayName("?????????????????? ????????????.")
    @Test
    void deleteStudylogTest() {
        // given
        List<StudylogResponse> studylogs = insertStudylogs(member1, studylog1);
        StudylogResponse studylog = studylogs.get(0);

        // when
        studylogService.deleteStudylog(member1.getId(), studylog.getId());

        //then
        Studylog deletedStudylog = studylogService.findStudylogById(studylog.getId());
        assertThat(deletedStudylog.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("????????? ??????????????? ?????? ??????")
    @Transactional
    void calendarStudylogTest() throws Exception {
        //given
        insertStudylogs(member1, studylog1, studylog2, studylog3);

        //when
        final List<CalendarStudylogResponse> calendarPosts =
            studylogService.findCalendarStudylogs(member1.getUsername(), LocalDate.now());

        //then
        assertThat(calendarPosts)
            .extracting(CalendarStudylogResponse::getTitle)
            .containsExactlyInAnyOrder(studylog1.getTitle(), studylog2.getTitle(), studylog3.getTitle());
    }

    @DisplayName("?????????????????? ????????????. - ?????? ??? studylogDocument??? ????????????.")
    @Test
    void delete() {
        // given
        List<StudylogResponse> studylogResponses = insertStudylogs(member1, studylog1);
        Long id = studylogResponses.get(0).getId();

        studylogService.deleteStudylog(member1.getId(), id);

        // when - then
        assertThatThrownBy(() -> studylogDocumentService.findById(id))
            .isInstanceOf(StudylogDocumentNotFoundException.class);
    }

    @DisplayName("RSS ????????? ????????????.")
    @Test
    void readRssFeeds() {
        // given
        insertStudylogs(member1, studylog1, studylog2);
        insertStudylogs(member2, studylog3);

        StudylogsResponse studylogs1 = studylogService
            .findStudylogsOf(member1.getUsername(), Pageable.unpaged());
        StudylogsResponse studylogs2 = studylogService
            .findStudylogsOf(member2.getUsername(), Pageable.unpaged());

        List<Long> studylogIds1 = studylogs1.getData().stream()
            .map(StudylogResponse::getId)
            .collect(toList());
        List<Long> studylogIds2 = studylogs2.getData().stream()
            .map(StudylogResponse::getId)
            .collect(toList());

        studylogIds1.addAll(studylogIds2);

        List<String> studylogLinks = studylogIds1.stream()
            .map(studylogId -> URL + "/studylogs/" + studylogId)
            .collect(toList());

        // when
        List<StudylogRssFeedResponse> responses = studylogService.readRssFeeds(URL);

        // then
        assertThat(responses).hasSize(3);
        assertThat(responses)
            .extracting(StudylogRssFeedResponse::getLink)
            .containsExactlyInAnyOrderElementsOf(studylogLinks);
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
}
