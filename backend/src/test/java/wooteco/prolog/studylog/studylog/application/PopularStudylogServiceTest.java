package wooteco.prolog.studylog.studylog.application;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;
import wooteco.prolog.member.domain.repository.MemberGroupRepository;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.application.PopularStudylogService;
import wooteco.prolog.studylog.application.StudylogLikeService;
import wooteco.prolog.studylog.application.StudylogScrapService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.Tag;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class PopularStudylogServiceTest {

    private static final String STUDYLOG1_TITLE = "이것은 제목";
    private static final String STUDYLOG2_TITLE = "이것은 두번째 제목";
    private static final String STUDYLOG3_TITLE = "이것은 3 제목";
    private static final String STUDYLOG4_TITLE = "이것은 네번 제목";
    private static final String STUDYLOG5_TITLE = "이것은 5 제목";

    private static Tag tag1 = new Tag(1L, "소롱의글쓰기");
    private static Tag tag2 = new Tag(2L, "스프링");
    private static Tag tag3 = new Tag(3L, "감자튀기기");
    private static Tag tag4 = new Tag(4L, "집필왕웨지");
    private static Tag tag5 = new Tag(5L, "피케이");
    private static List<Tag> tags = asList(
        tag1, tag2, tag3, tag4, tag5
    );

    private static final String URL = "http://localhost:8080";

    @Autowired
    private StudylogService studylogService;
    @Autowired
    private PopularStudylogService popularStudylogService;
    @Autowired
    private StudylogScrapService studylogScrapService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private StudylogLikeService studylogLikeService;
    @Autowired
    private MemberGroupRepository memberGroupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    private Member member1;
    private Member member2;
    private Member member3;

    private MemberGroup frontendMemberGroup;
    private MemberGroup backendMemberGroup;
    private MemberGroup mobileAndroidMemberGroup;

    private GroupMember frontendGroupMember;
    private GroupMember backendGroupMember;
    private GroupMember mobileAndroidGroupMember;

    private LoginMember frontendLoginMember;
    private LoginMember backendLoginMember;
    private LoginMember androidLoginMember;
    private LoginMember anonymousLoginMember;

    private Session session1;
    private Session session2;
    private Session session3;

    private Mission mission1;
    private Mission mission2;
    private Mission mission3;

    private Studylog studylog1;
    private Studylog studylog2;
    private Studylog studylog3;
    private Studylog studylog4;
    private Studylog studylog5;

    @BeforeEach
    void setUp() {
        SessionResponse sessionResponse1 = sessionService.create(
            new SessionRequest("백엔드Java 레벨1 - 2021"));
        SessionResponse sessionResponse2 = sessionService.create(
            new SessionRequest("프론트엔드JS 레벨1 - 2021"));
        SessionResponse sessionResponse3 = sessionService.create(
            new SessionRequest("모바일 안드로이드 레벨1 - 2021"));

        this.session1 = new Session(sessionResponse1.getId(), null, sessionResponse1.getName());
        this.session2 = new Session(sessionResponse2.getId(), null, sessionResponse2.getName());
        this.session3 = new Session(sessionResponse3.getId(), null, sessionResponse3.getName());

        MissionResponse missionResponse1 = missionService
            .create(new MissionRequest("자동차 미션", session1.getId()));
        MissionResponse missionResponse2 = missionService
            .create(new MissionRequest("수동차 미션", session2.getId()));
        MissionResponse missionResponse3 = missionService
            .create(new MissionRequest("안드로이드 미션", session3.getId()));

        this.mission1 = new Mission(missionResponse1.getId(), missionResponse1.getName(), session1);
        this.mission2 = new Mission(missionResponse2.getId(), missionResponse2.getName(), session2);
        this.mission3 = new Mission(missionResponse3.getId(), missionResponse3.getName(), session3);

        this.member1 = memberService.findOrCreateMember(
            new GithubProfileResponse("이름1", "별명1", "1", "image"));
        this.member2 = memberService.findOrCreateMember(
            new GithubProfileResponse("이름2", "별명2", "2", "image"));
        this.member3 = memberService.findOrCreateMember(
            new GithubProfileResponse("이름3", "별명3", "3", "image"));

        this.frontendMemberGroup = memberGroupRepository.save(
            new MemberGroup(null, "프론트엔드", "프론트엔드 설명")
        );
        this.backendMemberGroup = memberGroupRepository.save(
            new MemberGroup(null, "백엔드", "백엔드 설명")
        );
        this.mobileAndroidMemberGroup = memberGroupRepository.save(
            new MemberGroup(null, "안드로이드", "안드로이드 설명")
        );

        this.frontendGroupMember = groupMemberRepository.save(
            new GroupMember(null, member1, frontendMemberGroup)
        );
        this.backendGroupMember = groupMemberRepository.save(
            new GroupMember(null, member2, backendMemberGroup)
        );
        this.mobileAndroidGroupMember = groupMemberRepository.save(
            new GroupMember(null, member3, mobileAndroidMemberGroup)
        );

        this.frontendLoginMember = new LoginMember(member1.getId(), Authority.MEMBER);
        this.backendLoginMember = new LoginMember(member2.getId(), Authority.MEMBER);
        this.androidLoginMember = new LoginMember(member3.getId(), Authority.MEMBER);
        this.anonymousLoginMember = new LoginMember(null, Authority.ANONYMOUS);

        this.studylog1 = new Studylog(member1,
            STUDYLOG1_TITLE, "피케이와 포모의 스터디로그", mission1,
            asList(tag1, tag2));
        this.studylog2 = new Studylog(member1,
            STUDYLOG2_TITLE, "피케이와 포모의 스터디로그 2", mission1,
            asList(tag2, tag3));
        this.studylog3 = new Studylog(member2,
            STUDYLOG3_TITLE, "피케이 스터디로그", mission2,
            asList(tag3, tag4, tag5));
        this.studylog4 = new Studylog(member2,
            STUDYLOG4_TITLE, "포모의 스터디로그", mission2,
            asList(tag3, tag4, tag5));
        this.studylog5 = new Studylog(member3,
            STUDYLOG5_TITLE, "내용", mission3, emptyList());
    }

    @DisplayName("로그인하지 않은 상태에서 제시된 개수만큼 인기있는 스터디로그를 조회한다.")
    @ParameterizedTest
    @CsvSource(value = {"0, 3, 3, 3", "0, 2, 2, 2"})
    void findPopularStudylogsWithoutLogin(int page, int size, int totalSize, int dataSize) {
        // given
        insertStudylogs(member1, studylog1, studylog2);
        insertStudylogs(member2, studylog3);
        studylogService.retrieveStudylogById(anonymousLoginMember, 2L, false);
        studylogScrapService.registerScrap(member1.getId(), 2L);
        studylogService.retrieveStudylogById(anonymousLoginMember, 3L, false);
        studylogScrapService.registerScrap(member1.getId(), 3L);
        studylogLikeService.likeStudylog(member1.getId(), 3L, true);

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        popularStudylogService.updatePopularStudylogs(pageRequest);
        PopularStudylogsResponse studylogs = popularStudylogService.findPopularStudylogs(
            pageRequest,
            null,
            true
        );

        // then
        assertAll(
            () -> assertThat(studylogs.getAllResponse().getTotalSize()).isEqualTo(totalSize),
            () -> assertThat(studylogs.getAllResponse().getData()).hasSize(dataSize)
        );
        for (StudylogResponse response : studylogs.getAllResponse().getData()) {
            assertAll(
                () -> assertThat(response.isScrap()).isFalse(),
                () -> assertThat(response.isRead()).isFalse()
            );
        }
    }

    @DisplayName("로그인한 상태에서 제시된 개수만큼 인기있는 스터디로그를 조회한다.")
    @ParameterizedTest
    @CsvSource(value = {"0, 3, 3, 3", "0, 2, 2, 2"})
    void findPopularStudylogsWithLogin(int page, int size, int totalSize, int dataSize) {
        // given
        insertStudylogs(member1, studylog1, studylog2, studylog3);

        // 2번째 멤버가 1번째 멤버의 게시글 2번, 3번을 조회
        studylogService.retrieveStudylogById(backendLoginMember, 1L, false);
        studylogService.retrieveStudylogById(backendLoginMember, 2L, false);
        studylogService.retrieveStudylogById(backendLoginMember, 3L, false);

        // 2번, 3번 글 스크랩
        studylogScrapService.registerScrap(member2.getId(), 1L);
        studylogScrapService.registerScrap(member2.getId(), 2L);
        studylogScrapService.registerScrap(member2.getId(), 3L);

        // 3번 글 좋아요
        studylogLikeService.likeStudylog(member2.getId(), 3L, true);

        // when
        PageRequest pageRequest = PageRequest.of(page, size);
        popularStudylogService.updatePopularStudylogs(pageRequest);
        PopularStudylogsResponse studylogs = popularStudylogService.findPopularStudylogs(
            pageRequest,
            member2.getId(),
            member2.isAnonymous()
        );

        // then
        assertAll(
            () -> assertThat(studylogs.getAllResponse().getTotalSize()).isEqualTo(totalSize),
            () -> assertThat(studylogs.getAllResponse().getData()).hasSize(dataSize)
        );
        for (StudylogResponse response : studylogs.getAllResponse().getData()) {
            assertAll(
                () -> assertThat(response.isScrap()).isTrue(),
                () -> assertThat(response.isRead()).isTrue()
            );
        }
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

    private List<TagRequest> toTagRequests(Studylog studylog) {
        return studylog.getStudylogTags().stream()
            .map(studylogTag -> new TagRequest(studylogTag.getTag().getName()))
            .collect(toList());
    }
}
