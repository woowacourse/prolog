package wooteco.prolog.studylog.studylog.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

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
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.application.LevelService;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.LevelRequest;
import wooteco.prolog.studylog.application.dto.LevelResponse;
import wooteco.prolog.studylog.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.application.dto.TagResponse;
import wooteco.prolog.studylog.application.dto.search.StudyLogsSearchRequest;
import wooteco.prolog.studylog.domain.Level;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class StudylogServiceTest {

    private static final String STUDYLOG1_TITLE = "이것은 제목";
    private static final String STUDYLOG2_TITLE = "이것은 두번째 제목";
    private static final String STUDYLOG3_TITLE = "이것은 3 제목";
    private static final String STUDYLOG4_TITLE = "이것은 네번 제목";

    private static Tag tag1 = new Tag(1L, "소롱의글쓰기");
    private static Tag tag2 = new Tag(2L, "스프링");
    private static Tag tag3 = new Tag(3L, "감자튀기기");
    private static Tag tag4 = new Tag(4L, "집필왕웨지");
    private static Tag tag5 = new Tag(5L, "피케이");
    private static List<Tag> tags = asList(
        tag1, tag2, tag3, tag4, tag5
    );

    @Autowired
    private StudylogService studylogService;
    @Autowired
    private LevelService levelService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;

    private Member member1;
    private Member member2;

    private Level level1;
    private Level level2;

    private Mission mission1;
    private Mission mission2;

    private Studylog studylog1;
    private Studylog studylog2;
    private Studylog studylog3;
    private Studylog studylog4;

    @BeforeEach
    void setUp() {
        LevelResponse levelResponse1 = levelService.create(new LevelRequest("레벨1"));
        LevelResponse levelResponse2 = levelService.create(new LevelRequest("레벨2"));

        this.level1 = new Level(levelResponse1.getId(), levelResponse1.getName());
        this.level2 = new Level(levelResponse2.getId(), levelResponse2.getName());

        MissionResponse missionResponse1 = missionService
            .create(new MissionRequest("자동차 미션", level1.getId()));
        MissionResponse missionResponse2 = missionService
            .create(new MissionRequest("수동차 미션", level2.getId()));

        this.mission1 = new Mission(missionResponse1.getId(), missionResponse1.getName(), level1);
        this.mission2 = new Mission(missionResponse2.getId(), missionResponse2.getName(), level1);

        this.member1 = memberService
            .findOrCreateMember(new GithubProfileResponse("이름1", "별명1", "1", "image"));
        this.member2 = memberService
            .findOrCreateMember(new GithubProfileResponse("이름2", "별명2", "2", "image"));

        this.studylog1 = new Studylog(member1,
            STUDYLOG1_TITLE, "피케이와 포모의 스터디로그", mission1, asList(tag1, tag2));
        this.studylog2 = new Studylog(member1,
            STUDYLOG2_TITLE, "피케이와 포모의 스터디로그 2", mission1, asList(tag2, tag3));
        this.studylog3 = new Studylog(member2,
            STUDYLOG3_TITLE, "피케이 스터디로그", mission2, asList(tag3, tag4, tag5));
        this.studylog4 = new Studylog(member2, STUDYLOG4_TITLE, "포모의 스터디로그", mission2, asList());
    }

    @DisplayName("스터디로그 여러개 삽입")
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

    @DisplayName("검색 및 필터")
    @ParameterizedTest
    @MethodSource("findWithFilter")
    void findWithFilter(String keyword, List<Long> levelIds, List<Long> missionIds,
                        List<Long> tagIds, List<String> usernames,
                        List<String> expectedStudylogTitles) {
        //given
        insertStudylogs(member1, studylog1, studylog2);
        insertStudylogs(member2, studylog3, studylog4);

        // document 초기화 어떻게...
        StudylogsResponse studylogsResponse = studylogService.findStudylogsWithFilter(
            new StudyLogsSearchRequest(
                keyword,
                levelIds,
                missionIds,
                tagIds,
                usernames,
                PageRequest.of(0, 10)
            )
        );

        //then
        List<String> titles = studylogsResponse.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(toList());

        assertThat(titles).containsExactlyInAnyOrderElementsOf(
            expectedStudylogTitles
        );
    }

    private static Stream<Arguments> findWithFilter() {
        return Stream.of(
//            Arguments.of(null, emptyList(), emptyList(),
//                asList(tag1.getId(), tag2.getId(), tag3.getId()), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
//            Arguments.of(null, emptyList(), emptyList(), singletonList(tag2.getId()), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
//            Arguments.of("", emptyList(), emptyList(), singletonList(tag3.getId()), asList(),
//                asList(STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
//            Arguments.of("", emptyList(), singletonList(1L), emptyList(), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
//            Arguments.of("", emptyList(), singletonList(2L), emptyList(), asList(),
//                asList(STUDYLOG3_TITLE, STUDYLOG4_TITLE)),
//            Arguments.of("", emptyList(), singletonList(1L), singletonList(tag1.getId()), asList(),
//                singletonList(STUDYLOG1_TITLE)),
//            Arguments.of("", singletonList(1L), singletonList(1L),
//                asList(tag1.getId(), tag2.getId(), tag3.getId()), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
//            Arguments.of("", emptyList(), singletonList(1L), singletonList(tag2.getId()), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE)),
//            Arguments.of("", emptyList(), asList(1L, 2L), singletonList(tag3.getId()), asList(),
//                asList(STUDYLOG2_TITLE, STUDYLOG3_TITLE)),
//            Arguments.of("", emptyList(), emptyList(), emptyList(), asList(),
//                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE, STUDYLOG4_TITLE)),
            Arguments.of("이것은 제목", emptyList(), emptyList(), emptyList(), asList(),
                asList(STUDYLOG1_TITLE, STUDYLOG2_TITLE, STUDYLOG3_TITLE, STUDYLOG4_TITLE)),
            Arguments.of("궁둥이", emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        );
    }

    @DisplayName("유저 이름으로 스터디로그를 조회한다.")
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

    @DisplayName("유저 이름으로 스터디로그를 조회한다 - 페이징")
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

    @DisplayName("스터디로그를 수정한다")
    @Test
    void updateStudylogTest() {
        //given
        List<StudylogResponse> studylogRespons = insertStudylogs(member1, studylog1);
        StudylogResponse targetStudylog = studylogRespons.get(0);
        StudylogRequest updateStudylogRequest = new StudylogRequest("updateTitle", "updateContent",
            2L,
            toTagRequests(tags));

        //when
        studylogService.updateStudylog(member1, targetStudylog.getId(), updateStudylogRequest);

        //then
        StudylogResponse expectedResult = studylogService.findById(targetStudylog.getId());
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

    @DisplayName("스터디로그를 삭제한다")
    @Test
    void deleteStudylogTest() {
        //given
        List<StudylogResponse> studylogRespons = insertStudylogs(member1, studylog1, studylog2,
            studylog3,
            studylog4);

        //when
        List<Long> studylogIds = studylogRespons.stream()
            .map(StudylogResponse::getId)
            .collect(toList());

        Long removedId = studylogIds.remove(0);
        studylogService.deleteStudylog(member1, removedId);

        //then
        StudylogsResponse studylogsResponse = studylogService
            .findStudylogsOf(member1.getUsername(), Pageable.unpaged());

        List<Long> expectedIds = studylogsResponse.getData().stream()
            .map(StudylogResponse::getId)
            .collect(toList());

        assertThat(expectedIds).containsExactlyElementsOf(studylogIds);
    }

    private List<StudylogResponse> insertStudylogs(Member member, List<Studylog> studylogs) {
        List<StudylogRequest> studylogRequests = studylogs.stream()
            .map(studylog ->
                new StudylogRequest(
                    studylog.getTitle(),
                    studylog.getContent(),
                    studylog.getMission().getId(),
                    toTagRequests(studylog)
                )
            )
            .collect(toList());

        return studylogService.insertStudylogs(member, studylogRequests);
    }

    private List<StudylogResponse> insertStudylogs(Member member, Studylog... studylogs) {
        return insertStudylogs(member, asList(studylogs));
    }

    private List<TagRequest> toTagRequests(Studylog studylog) {
        return studylog.getStudylogTags().stream()
            .map(studylogTag -> new TagRequest(studylogTag.getTag().getName()))
            .collect(toList());
    }

    private List<TagRequest> toTagRequests(List<Tag> tags) {
        return tags.stream()
            .map(tag -> new TagRequest(tag.getName()))
            .collect(toList());
    }
}
