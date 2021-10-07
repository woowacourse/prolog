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
import wooteco.prolog.report.application.dto.ability.AbilityCreateRequest;
import wooteco.prolog.report.application.dto.ability.AbilityResponse;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.report.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.exception.AbilityNotFoundException;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.LevelService;
import wooteco.prolog.studylog.application.MissionService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
import wooteco.prolog.studylog.application.dto.LevelRequest;
import wooteco.prolog.studylog.application.dto.LevelResponse;
import wooteco.prolog.studylog.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.MissionResponse;
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
    private DocumentService studyLogDocumentService;
    private AbilityService abilityService;
    private UpdatedContentsRepository updatedContentsRepository;
    private ReportService reportService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        studyLogDocumentService.deleteAll();

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

        // ability init
        Abilities.initBackend(Members.BROWN.value.getId(), abilityService);
        Abilities.initBackend(Members.JOANNE.value.getId(), abilityService);
        Abilities.initFrontend(Members.TYCHE.value.getId(), abilityService);
        Abilities.initFrontend(Members.SUNNY.value.getId(), abilityService);

        ReportGenerator.generate(3, Members.TYCHE.value, abilityService, studylogService)
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
                                             StudylogService studylogService) {
            ArrayList<ReportRequest> result = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                result.add(create(member, abilityService, studylogService));
            }

            return result;

        }

        private static ReportRequest create(Member member,
                                            AbilityService abilityService,
                                            StudylogService studylogService) {
            return new ReportRequest(
                null,
                "타이틀 입니다 " + cnt,
                "설명 입니다 " + cnt++,
                createGraphRequest(member, abilityService),
                createReportStudylogRequest(member, studylogService, abilityService),
                true
            );
        }

        private static List<ReportStudylogRequest> createReportStudylogRequest(Member member,
                                                                               StudylogService studylogService,
                                                                               AbilityService abilityService) {
            List<ReportStudylogRequest> studylogs = studylogService
                .findStudylogsOf(member.getUsername(), PageRequest.of(0, 20))
                .getData().stream()
                .map(studylogResponse -> new ReportStudylogRequest(
                    studylogResponse.getId(),
                    getRandomAbilitiesOf(member, abilityService)
                )).collect(toList());

            Collections.shuffle(studylogs);
            return studylogs.subList(0, new Random().nextInt(studylogs.size()));
        }

        private static GraphRequest createGraphRequest(Member member,
                                                       AbilityService abilityService) {
            return new GraphRequest(getParentAbilitiesOf(member, abilityService));
        }

        private static List<Long> getRandomAbilitiesOf(Member member,
                                                       AbilityService abilityService) {
            List<AbilityResponse> abilitiesByMember = abilityService.findAbilitiesByMember(member);
            Collections.shuffle(abilitiesByMember);
            return abilitiesByMember.subList(0, new Random().nextInt(abilitiesByMember.size()))
                .stream()
                .map(AbilityResponse::getId)
                .collect(toList());
        }

        private static List<AbilityRequest> getParentAbilitiesOf(Member member,
                                                                 AbilityService abilityService) {
            return abilityService.findParentAbilitiesByMember(member).stream()
                .map(abilityResponse -> new AbilityRequest(
                    abilityResponse.getId(),
                    1L,
                    true))
                .collect(toList());
        }
    }

    private static class Abilities {

        public static void initBackend(Long memberId, AbilityService abilityService) {
            // init parent
            AbilityCreateRequest programmingRequest = new AbilityCreateRequest("Programming", "Programming 입니다.", "#ff9100", null);
            AbilityCreateRequest designRequest = new AbilityCreateRequest("Design", "Design 입니다.", "#00cccc", null);
            AbilityCreateRequest infrastructureRequest = new AbilityCreateRequest("Infrastructure", "Infrastructure 입니다.", "#ccccff", null);
            AbilityCreateRequest softwareDevelopmentProcessAndMaintenanceRequest = new AbilityCreateRequest("Software Development Process & Maintenance", "Software Development Process & Maintenance 입니다.", "#ffcce5", null);
            AbilityCreateRequest csRequest = new AbilityCreateRequest("CS", "CS 입니다.", "#9100ff", null);
            abilityService.createAbility(memberId, programmingRequest);
            abilityService.createAbility(memberId, designRequest);
            abilityService.createAbility(memberId, infrastructureRequest);
            abilityService.createAbility(memberId, softwareDevelopmentProcessAndMaintenanceRequest);
            abilityService.createAbility(memberId, csRequest);

            List<AbilityResponse> abilityResponses = abilityService.findParentAbilitiesByMemberId(memberId);

            // init children
            AbilityResponse programmingResponse = findParentAbilityResponse(abilityResponses, programmingRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("Language", "Language 입니다.", programmingResponse.getColor(), programmingResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Framework", "Framework 입니다.", programmingResponse.getColor(), programmingResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Testing", "Testing 입니다.", programmingResponse.getColor(), programmingResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Web Programming", "Web Programming 입니다.", programmingResponse.getColor(), programmingResponse.getId()));

            AbilityResponse designResponse = findParentAbilityResponse(abilityResponses, designRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("Development Driven", "Development Driven 입니다.", designResponse.getColor(), designResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Programming Principle", "Programming Principle 입니다.", designResponse.getColor(), designResponse.getId()));

            AbilityResponse infrastructureResponse = findParentAbilityResponse(abilityResponses, infrastructureRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("Web Architecture Components", "Web Architecture Components 입니다.", infrastructureResponse.getColor(), infrastructureResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Service & Tools", "Service & Tools 입니다.", infrastructureResponse.getColor(), infrastructureResponse.getId()));

            AbilityResponse softwareDevelopmentProcessAndMaintenanceResponse = findParentAbilityResponse(abilityResponses, softwareDevelopmentProcessAndMaintenanceRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("Development Process", "Development Process 입니다.", softwareDevelopmentProcessAndMaintenanceResponse.getColor(), softwareDevelopmentProcessAndMaintenanceResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Maintenance", "Maintenance 입니다.", softwareDevelopmentProcessAndMaintenanceResponse.getColor(), softwareDevelopmentProcessAndMaintenanceResponse.getId()));

            AbilityResponse csResponse = findParentAbilityResponse(abilityResponses, csRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("Database", "Database 입니다.", csResponse.getColor(), csResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("네트워크", "네트워크 입니다.", csResponse.getColor(), csResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("OS", "OS 입니다.", csResponse.getColor(), csResponse.getId()));
        }

        public static void initFrontend(Long memberId, AbilityService abilityService) {
            // init parent
            AbilityCreateRequest javaScriptAndHtmlRequest = new AbilityCreateRequest("JavaScript & HTML", "JavaScript & HTML 입니다.", "#ff009a", null);
            AbilityCreateRequest csRequest = new AbilityCreateRequest("CS", "CS 입니다.", "#9100ff", null);
            AbilityCreateRequest graphicsRequest = new AbilityCreateRequest("Graphics", "Graphics 입니다.", "#2f8aff", null);
            AbilityCreateRequest architectureRequest = new AbilityCreateRequest("Architecture", "Architecture 입니다.", "#e5ffcc", null);
            AbilityCreateRequest uiuxRequest = new AbilityCreateRequest("UI/UX", "UI/UX 입니다.", "#2fff6e", null);
            abilityService.createAbility(memberId, javaScriptAndHtmlRequest);
            abilityService.createAbility(memberId, csRequest);
            abilityService.createAbility(memberId, graphicsRequest);
            abilityService.createAbility(memberId, uiuxRequest);
            abilityService.createAbility(memberId, architectureRequest);

            List<AbilityResponse> abilityResponses = abilityService.findParentAbilitiesByMemberId(memberId);

            AbilityResponse javaScriptAndHtmlResponse = findParentAbilityResponse(abilityResponses, javaScriptAndHtmlRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("기초 언어 시스템", "기초 언어 시스템 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("고급 언어 시스템", "고급 언어 시스템 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("브라우저", "브라우저 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("비동기&데이터 처리", "비동기&데이터 처리 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("성능", "성능 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("HTML", "HTML 입니다.", javaScriptAndHtmlResponse.getColor(), javaScriptAndHtmlResponse.getId()));

            AbilityResponse csResponse = findParentAbilityResponse(abilityResponses, csRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("네트워크", "네트워크 입니다.", csResponse.getColor(), csResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("알고리즘", "알고리즘 입니다.", csResponse.getColor(), csResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("자료구조", "자료구조 입니다.", csResponse.getColor(), csResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("OS", "OS 입니다.", csResponse.getColor(), csResponse.getId()));

            AbilityResponse graphicsResponse = findParentAbilityResponse(abilityResponses, graphicsRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("CSS 스타일링: 레이아웃과 포지셔닝", "CSS 스타일링: 레이아웃과 포지셔닝 입니다.", graphicsResponse.getColor(), graphicsResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("트랜지션, 애니메이션", "트랜지션, 애니메이션 입니다.", graphicsResponse.getColor(), graphicsResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("2D 그래픽스", "2D 그래픽스 입니다.", graphicsResponse.getColor(), graphicsResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("3D 그래픽스", "3D 그래픽스 입니다.", graphicsResponse.getColor(), graphicsResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("Vector 그래픽스", "Vector 그래픽스 입니다.", graphicsResponse.getColor(), graphicsResponse.getId()));

            AbilityResponse uiuxResponse = findParentAbilityResponse(abilityResponses, uiuxRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("인터랙션 디자인 이론", "인터랙션 디자인 이론 입니다.", uiuxResponse.getColor(), uiuxResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("UI/UX 설계", "UI/UX 설계 입니다.", uiuxResponse.getColor(), uiuxResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("UX 일반 이론", "UX 일반 이론 입니다.", uiuxResponse.getColor(), uiuxResponse.getId()));

            AbilityResponse architectureResponse = findParentAbilityResponse(abilityResponses, architectureRequest);
            abilityService.createAbility(memberId, new AbilityCreateRequest("웹애플리케이션 구현 시 고려해야 하는 설계", "웹애플리케이션 구현 시 고려해야 하는 설계 입니다.", architectureResponse.getColor(), architectureResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("프로젝트 개발 환경 설계", "프로젝트 개발 환경 설계 입니다.", architectureResponse.getColor(), architectureResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("테스트와 프로젝트 배포", "테스트와 프로젝트 배포 입니다.", architectureResponse.getColor(), architectureResponse.getId()));
            abilityService.createAbility(memberId, new AbilityCreateRequest("프로그래밍 테크닉, 개발 프랙티스, 개발 방법론", "프로그래밍 테크닉, 개발 프랙티스, 개발 방법론 입니다.", architectureResponse.getColor(), architectureResponse.getId()));
        }

        private static AbilityResponse findParentAbilityResponse(
            List<AbilityResponse> abilityResponses, AbilityCreateRequest request) {
            return abilityResponses.stream()
                .filter(response -> request.getName().equals(response.getName()))
                .findAny()
                .orElseThrow(AbilityNotFoundException::new);
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
        BROWN(
            new GithubProfileResponse(
                "류성현",
                "gracefulBrown",
                "46308949",
                "https://avatars.githubusercontent.com/u/46308949?v=4")
        ),
        JOANNE(new GithubProfileResponse(
            "서민정",
            "seovalue",
            "123456",
            "https://avatars.githubusercontent.com/u/48412963?v=4")
        ),
        TYCHE(new GithubProfileResponse(
            "티케",
            "devhyun637",
            "59258239",
            "https://avatars.githubusercontent.com/u/59258239?v=4")
        ),
        SUNNY(new GithubProfileResponse(
                "박선희",
                "서니",
                "67677561",
                "https://avatars.githubusercontent.com/u/67677561?v=4"
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
