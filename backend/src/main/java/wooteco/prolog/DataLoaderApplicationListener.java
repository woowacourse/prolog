package wooteco.prolog;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.login.ui.LoginMember.Authority;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.application.AbilityService;
import wooteco.prolog.report.application.ReportService;
import wooteco.prolog.report.application.dto.ability.DefaultAbilityCreateRequest;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.report.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.domain.ablity.Ability;
import wooteco.prolog.report.domain.ablity.repository.AbilityRepository;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.session.application.LevelService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.update.UpdateContent;
import wooteco.prolog.update.UpdatedContents;
import wooteco.prolog.update.UpdatedContentsRepository;

@Profile({"local"})
@AllArgsConstructor
@Configuration
public class DataLoaderApplicationListener implements
    ApplicationListener<ContextRefreshedEvent> {

    private LevelService levelService;
    private MissionService missionService;
    private TagService tagService;
    private MemberService memberService;
    private StudylogService studylogService;
    private DocumentService studylogDocumentService;
    private AbilityService abilityService;
    private UpdatedContentsRepository updatedContentsRepository;
    private ReportService reportService;
    private AbilityRepository abilityRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        studylogDocumentService.deleteAll();

        // level init
        Levels.init(levelService);

        // mission init
        Missions.init(missionService);

        // filter init
        TagRequests.init(tagService);

        // member init
        Members.init(memberService);

        // post init
        studylogService.insertStudylogs(Members.BROWN.value.getId(), StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.JOANNE.value.getId() , StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.TYCHE.value.getId(), StudylogGenerator.generate(100));
        studylogService.insertStudylogs(Members.SUNNY.value.getId(), StudylogGenerator.generate(20));

        // defaultAbility init
        Long csId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("CS", "CS 입니다.", "#9100ff", "common"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Database", "Database 입니다.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("네트워크", "네트워크 입니다.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("OS", "OS 입니다.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("알고리즘", "알고리즘 입니다.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("자료구조", "자료구조 입니다.", "#9100ff", "common", csId));

        Long programmingId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Programming", "Programming 입니다.", "#ff9100", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Language", "Language 입니다.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Framework", "Framework 입니다.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Testing", "Testing 입니다.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Web Programming", "Web Programming 입니다.", "#ff9100", "be", programmingId));

        Long designId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Design", "Design 입니다.", "#00cccc", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Development Driven", "Development Driven 입니다.", "#00cccc", "be", designId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Programming Principle", "Programming Principle 입니다.", "#00cccc", "be", designId));

        Long infrastructureId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Infrastructure", "Infrastructure 입니다.", "#ccccff", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Web Architecture Components", "Web Architecture Components 입니다.", "#ccccff", "be", infrastructureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Service & Tools", "Service & Tools 입니다.", "#ccccff", "be", infrastructureId));

        Long developmentId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Software Development Process & Maintenance", "Software Development Process & Maintenance 입니다.", "#ffcce5", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Development Process", "Development Process 입니다.", "#ffcce5", "be", developmentId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Maintenance", "Maintenance 입니다.", "#ffcce5", "be", developmentId));

        Long jsHtmlId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("JavaScript & HTML", "JavaScript & HTML 입니다.", "#ff009a", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("기초 언어 시스템", "기초 언어 시스템 입니다.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("고급 언어 시스템", "고급 언어 시스템 입니다.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("브라우저", "브라우저 입니다.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("비동기&데이터 처리", "비동기&데이터 처리 입니다.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("성능", "성능 입니다.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("HTML", "HTML 입니다.", "#ff009a", "fe", jsHtmlId));

        Long graphicsId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Graphics", "Graphics 입니다.", "#2f8aff", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("CSS 스타일링: 레이아웃과 포지셔닝", "CSS 스타일링: 레이아웃과 포지셔닝 입니다.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("트랜지션, 애니메이션", "트랜지션, 애니메이션 입니다.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("2D 그래픽스", "2D 그래픽스 입니다.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("3D 그래픽스", "3D 그래픽스 입니다.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Vector 그래픽스", "Vector 그래픽스 입니다.", "#2f8aff", "fe", graphicsId));

        Long architectureId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Architecture", "Architecture 입니다.", "#e5ffcc", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("인터랙션 디자인 이론", "인터랙션 디자인 이론 입니다.", "#e5ffcc", "fe", architectureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UI/UX 설계", "UI/UX 설계 입니다.", "#e5ffcc", "fe", architectureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UX 일반 이론", "UX 일반 이론 입니다.", "#e5ffcc", "fe", architectureId));

        Long uiuxId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UI/UX", "UI/UX 입니다.", "#2fff6e", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("웹애플리케이션 구현 시 고려해야 하는 설계", "웹애플리케이션 구현 시 고려해야 하는 설계 입니다.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("프로젝트 개발 환경 설계", "프로젝트 개발 환경 설계 입니다.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("테스트와 프로젝트 배포", "테스트와 프로젝트 배포 입니다.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("프로그래밍 테크닉, 개발 프랙티스, 개발 방법론", "프로그래밍 테크닉, 개발 프랙티스, 개발 방법론 입니다.", "#2fff6e", "fe", uiuxId));

        // ability init
        abilityService.addDefaultAbilities(Members.BROWN.value.getId(), "be");
        abilityService.addDefaultAbilities(Members.JOANNE.value.getId(), "be");
        abilityService.addDefaultAbilities(Members.TYCHE.value.getId(), "fe");
        abilityService.addDefaultAbilities(Members.SUNNY.value.getId(), "fe");

        ReportGenerator
            .generate(3, Members.TYCHE.value, abilityService, studylogService, abilityRepository)
            .forEach(reportRequest -> reportService.createReport(
                reportRequest,
                new LoginMember(Members.TYCHE.value.getId(), Authority.MEMBER)
            ));

        updatedContentsRepository
            .save(new UpdatedContents(null, UpdateContent.MEMBER_TAG_UPDATE, 1));
    }

    public static class ReportGenerator {

        private static int cnt = 1;

        public static List<ReportRequest> generate(int size, Member member,
                                                   AbilityService abilityService,
                                                   StudylogService studylogService,
                                                   AbilityRepository abilityRepository) {
            ArrayList<ReportRequest> result = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                result.add(create(member, abilityService, studylogService, abilityRepository));
            }

            return result;

        }

        private static ReportRequest create(Member member,
                                            AbilityService abilityService,
                                            StudylogService studylogService,
                                            AbilityRepository abilityRepository) {
            GraphRequest graphRequest = createGraphRequest(member, abilityService);
            return new ReportRequest(
                null,
                "타이틀 입니다 " + cnt,
                "설명 입니다 " + cnt++,
                graphRequest,
                createReportStudylogRequest(member, graphRequest, studylogService,
                    abilityRepository),
                true
            );
        }

        private static List<ReportStudylogRequest> createReportStudylogRequest(Member member,
                                                                               GraphRequest graphRequest,
                                                                               StudylogService studylogService,
                                                                               AbilityRepository abilityRepository) {
            List<ReportStudylogRequest> studylogs = studylogService
                .findStudylogsOf(member.getUsername(), PageRequest.of(0, 20))
                .getData().stream()
                .map(studylogResponse -> new ReportStudylogRequest(
                    studylogResponse.getId(),
                    getRandomAbilitiesOf(graphRequest, abilityRepository)
                )).collect(toList());

            Collections.shuffle(studylogs);
            return studylogs.subList(0, new Random().nextInt(studylogs.size()));
        }

        private static GraphRequest createGraphRequest(Member member,
                                                       AbilityService abilityService) {
            return new GraphRequest(getParentAbilitiesOf(member, abilityService));
        }

        private static List<Long> getRandomAbilitiesOf(GraphRequest graphRequest,
                                                       AbilityRepository abilityRepository) {
            List<Long> abilityIds = graphRequest.getAbilities().stream()
                .map(AbilityRequest::getId)
                .collect(toList());

            List<Ability> children = abilityRepository
                .findChildrenAbilitiesByParentId(abilityIds);

            Collections.shuffle(children);
            return children.subList(0, new Random().nextInt(children.size()))
                .stream()
                .map(Ability::getId)
                .collect(toList());
        }

        private static List<AbilityRequest> getParentAbilitiesOf(Member member,
                                                                 AbilityService abilityService) {
            return abilityService.findParentAbilitiesByMemberId(member.getId()).stream()
                .map(abilityResponse -> new AbilityRequest(
                    abilityResponse.getId(),
                    1L,
                    true))
                .collect(toList());
        }
    }

    private enum Levels {
        LEVEL1(new LevelRequest("백엔드Java 레벨1 - 2021")),
        LEVEL2(new LevelRequest("프론트엔드JS 레벨1 - 2021")),
        LEVEL3(new LevelRequest("백엔드Java 레벨2 - 2021")),
        LEVEL4(new LevelRequest("프론트엔드JS 레벨2 - 2021"));

        private final LevelRequest request;
        private LevelResponse response;

        Levels(LevelRequest levelRequest) {
            this.request = levelRequest;
        }

        public static void init(LevelService levelService) {
            for (Levels level : values()) {
                level.response = levelService.create(level.request);
            }
        }

        public Long getId() {
            return response.getId();
        }
    }

    private enum Missions {
        MISSION1(new MissionRequest("자동차경주", Levels.LEVEL1.getId())),
        MISSION2(new MissionRequest("로또", Levels.LEVEL2.getId())),
        MISSION3(new MissionRequest("장바구니", Levels.LEVEL3.getId())),
        MISSION4(new MissionRequest("지하철", Levels.LEVEL4.getId()));

        private final MissionRequest request;
        private MissionResponse response;

        Missions(MissionRequest request) {
            this.request = request;
        }

        public static void init(MissionService missionService) {
            for (Missions mission : values()) {
                mission.response = missionService.create(mission.request);
            }
        }

        public Long getId() {
            return response.getId();
        }

    }

    private enum TagRequests {
        TAG_REQUESTS_1234(Arrays.asList(
            new TagRequest("자바"),
            new TagRequest("자바스크립트"),
            new TagRequest("스프링"),
            new TagRequest("리액트")
        )),
        TAG_REQUESTS_EMPTY(Collections.emptyList()),
        TAG_REQUESTS_12(Arrays.asList(
            new TagRequest("자바"),
            new TagRequest("자바스크립트")
        )),
        TAG_REQUESTS_3(Collections.singletonList(
            new TagRequest("스프링")
        ));

        private static final Random random = new Random();

        private final List<TagRequest> value;

        TagRequests(List<TagRequest> tagRequests) {
            this.value = tagRequests;
        }

        public static void init(TagService tagService) {
            Arrays.asList(values())
                .forEach(tagRequests -> tagService.findOrCreate(tagRequests.value));
        }

        public static List<TagRequest> random() {
            int i = random.nextInt(values().length);
            return values()[i].value;
        }
    }

    public enum Members {
        BROWN(new GithubProfileResponse(
            "류성현",
            "gracefulBrown",
            "46308949",
            "https://avatars.githubusercontent.com/u/46308949?v=4"
        )),
        JOANNE(new GithubProfileResponse(
            "서민정",
            "seovalue",
            "123456",
            "https://avatars.githubusercontent.com/u/48412963?v=4"
        )),
        TYCHE(new GithubProfileResponse(
            "티케",
            "devhyun637",
            "59258239",
            "https://avatars.githubusercontent.com/u/59258239?v=4"
        )),
        SUNNY(new GithubProfileResponse(
            "박선희",
            "서니",
            "67677561",
            "https://avatars.githubusercontent.com/u/67677561?v=4"
        )),
        HYEON9MAK(new GithubProfileResponse(
           "최현구",
           "hyeon9mak",
           "37354145",
            "https://avatars.githubusercontent.com/u/37354145?v=4"
        ));

        private final GithubProfileResponse githubProfileResponse;
        private Member value;

        Members(GithubProfileResponse githubProfileResponse) {
            this.githubProfileResponse = githubProfileResponse;
        }

        public Member getValue() {
            return value;
        }

        public static void init(MemberService memberService) {
            for (Members member : values()) {
                member.value = memberService
                    .findOrCreateMember(member.githubProfileResponse);
            }
        }
    }

    private static class StudylogGenerator {

        private static int cnt = 0;

        private StudylogGenerator() {
        }

        public static List<StudylogRequest> generate(int size) {
            List<StudylogRequest> result = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                result.add(create());
            }

            return result;
        }

        private static StudylogRequest create() {
            return new StudylogRequest(
                "페이지네이션 데이터 " + cnt,
                "좋은 내용",
                Missions.values()[cnt++ % Missions.values().length].getId(),
                TagRequests.random()
            );
        }
    }
}
