package wooteco.prolog;

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
import org.testcontainers.shaded.com.google.common.collect.Lists;
import wooteco.prolog.ability.application.AbilityService;
import wooteco.prolog.ability.application.dto.DefaultAbilityCreateRequest;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.SessionMemberService;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.dto.SessionMemberRequest;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.DocumentService;
import wooteco.prolog.studylog.application.PopularStudylogService;
import wooteco.prolog.studylog.application.StudylogService;
import wooteco.prolog.studylog.application.TagService;
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

    private SessionService sessionService;
    private SessionMemberService sessionMemberService;
    private MissionService missionService;
    private TagService tagService;
    private MemberService memberService;
    private StudylogService studylogService;
    private DocumentService studylogDocumentService;
    private AbilityService abilityService;
    private UpdatedContentsRepository updatedContentsRepository;
    private PopularStudylogService popularStudylogService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        studylogDocumentService.deleteAll();

        // session init
        Sessions.init(sessionService);

        // mission init
        Missions.init(missionService);

        // filter init
        TagRequests.init(tagService);

        // member init
        Members.init(memberService);

        sessionMemberService.registerMembers(1L, new SessionMemberRequest(Lists.newArrayList(Members.BROWN.value.getId(), Members.SUNNY.value.getId())));

        // post init
        studylogService.insertStudylogs(Members.BROWN.value.getId(), StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.JOANNE.value.getId(), StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.TYCHE.value.getId(), StudylogGenerator.generate(100));
        studylogService.insertStudylogs(Members.SUNNY.value.getId(), StudylogGenerator.generate(20));

        // defaultAbility init
        Long csId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("CS", "CS ?????????.", "#9100ff", "common"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Database", "Database ?????????.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????", "???????????? ?????????.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("OS", "OS ?????????.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????", "???????????? ?????????.", "#9100ff", "common", csId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????", "???????????? ?????????.", "#9100ff", "common", csId));

        Long programmingId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Programming", "Programming ?????????.", "#ff9100", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Language", "Language ?????????.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Framework", "Framework ?????????.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Testing", "Testing ?????????.", "#ff9100", "be", programmingId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Web Programming", "Web Programming ?????????.", "#ff9100", "be", programmingId));

        Long designId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Design", "Design ?????????.", "#00cccc", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Development Driven", "Development Driven ?????????.", "#00cccc", "be", designId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Programming Principle", "Programming Principle ?????????.", "#00cccc", "be", designId));

        Long infrastructureId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Infrastructure", "Infrastructure ?????????.", "#ccccff", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Web Architecture Components", "Web Architecture Components ?????????.", "#ccccff", "be", infrastructureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Service & Tools", "Service & Tools ?????????.", "#ccccff", "be", infrastructureId));

        Long developmentId = abilityService.createDefaultAbility(
            new DefaultAbilityCreateRequest("Software Development Process & Maintenance", "Software Development Process & Maintenance ?????????.", "#ffcce5", "be"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Development Process", "Development Process ?????????.", "#ffcce5", "be", developmentId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Maintenance", "Maintenance ?????????.", "#ffcce5", "be", developmentId));

        Long jsHtmlId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("JavaScript & HTML", "JavaScript & HTML ?????????.", "#ff009a", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("?????? ?????? ?????????", "?????? ?????? ????????? ?????????.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("?????? ?????? ?????????", "?????? ?????? ????????? ?????????.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????", "???????????? ?????????.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("?????????&????????? ??????", "?????????&????????? ?????? ?????????.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("??????", "?????? ?????????.", "#ff009a", "fe", jsHtmlId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("HTML", "HTML ?????????.", "#ff009a", "fe", jsHtmlId));

        Long graphicsId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Graphics", "Graphics ?????????.", "#2f8aff", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("CSS ????????????: ??????????????? ????????????", "CSS ????????????: ??????????????? ???????????? ?????????.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????, ???????????????", "????????????, ??????????????? ?????????.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("2D ????????????", "2D ???????????? ?????????.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("3D ????????????", "3D ???????????? ?????????.", "#2f8aff", "fe", graphicsId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Vector ????????????", "Vector ???????????? ?????????.", "#2f8aff", "fe", graphicsId));

        Long architectureId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("Architecture", "Architecture ?????????.", "#e5ffcc", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("???????????? ????????? ??????", "???????????? ????????? ?????? ?????????.", "#e5ffcc", "fe", architectureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UI/UX ??????", "UI/UX ?????? ?????????.", "#e5ffcc", "fe", architectureId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UX ?????? ??????", "UX ?????? ?????? ?????????.", "#e5ffcc", "fe", architectureId));

        Long uiuxId = abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("UI/UX", "UI/UX ?????????.", "#2fff6e", "fe"));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("????????????????????? ?????? ??? ???????????? ?????? ??????", "????????????????????? ?????? ??? ???????????? ?????? ?????? ?????????.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("???????????? ?????? ?????? ??????", "???????????? ?????? ?????? ?????? ?????????.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("???????????? ???????????? ??????", "???????????? ???????????? ?????? ?????????.", "#2fff6e", "fe", uiuxId));
        abilityService.createDefaultAbility(new DefaultAbilityCreateRequest("??????????????? ?????????, ?????? ????????????, ?????? ?????????", "??????????????? ?????????, ?????? ????????????, ?????? ????????? ?????????.", "#2fff6e", "fe", uiuxId));

        // ability init
//        abilityService.addDefaultAbilities(Members.BROWN.value.getId(), "be");
        abilityService.applyDefaultAbilities(Members.JOANNE.value.getId(), "be");
        abilityService.applyDefaultAbilities(Members.SUNNY.value.getId(), "fe");

        updatedContentsRepository.save(new UpdatedContents(null, UpdateContent.MEMBER_TAG_UPDATE, 1));
        PageRequest pageRequest = PageRequest.of(0, 10);
        popularStudylogService.updatePopularStudylogs(pageRequest);
    }

    private enum Sessions {
        LEVEL1(new SessionRequest("?????????Java ??????1 - 2021")),
        LEVEL3(new SessionRequest("?????????Java ??????2 - 2021")),
        LEVEL2(new SessionRequest("???????????????JS ??????1 - 2021")),
        LEVEL4(new SessionRequest("???????????????JS ??????2 - 2021"));

        private final SessionRequest request;
        private SessionResponse response;

        Sessions(SessionRequest sessionRequest) {
            this.request = sessionRequest;
        }

        public static void init(SessionService sessionService) {
            for (Sessions session : values()) {
                session.response = sessionService.create(session.request);
            }
        }

        public Long getId() {
            return response.getId();
        }
    }

    private enum Missions {
        MISSION1(new MissionRequest("???????????????", Sessions.LEVEL1.getId())),
        MISSION2(new MissionRequest("??????", Sessions.LEVEL2.getId())),
        MISSION3(new MissionRequest("????????????", Sessions.LEVEL3.getId())),
        MISSION4(new MissionRequest("?????????", Sessions.LEVEL4.getId()));

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
            new TagRequest("??????"),
            new TagRequest("??????????????????"),
            new TagRequest("?????????"),
            new TagRequest("?????????")
        )),
        TAG_REQUESTS_EMPTY(Collections.emptyList()),
        TAG_REQUESTS_12(Arrays.asList(
            new TagRequest("??????"),
            new TagRequest("??????????????????")
        )),
        TAG_REQUESTS_3(Collections.singletonList(
            new TagRequest("?????????")
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
            "?????????",
            "gracefulBrown",
            "46308949",
            "https://avatars.githubusercontent.com/u/46308949?v=4"
        )),
        JOANNE(new GithubProfileResponse(
            "?????????",
            "seovalue",
            "123456",
            "https://avatars.githubusercontent.com/u/48412963?v=4"
        )),
        TYCHE(new GithubProfileResponse(
            "??????",
            "devhyun637",
            "59258239",
            "https://avatars.githubusercontent.com/u/59258239?v=4"
        )),
        SUNNY(new GithubProfileResponse(
            "?????????",
            "??????",
            "67677561",
            "https://avatars.githubusercontent.com/u/67677561?v=4"
        )),
        HYEON9MAK(new GithubProfileResponse(
            "?????????",
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
                "?????????????????? ????????? " + cnt,
                "?????? ??????" + cnt,
                Sessions.values()[cnt++ % Missions.values().length].getId(),
                Missions.values()[cnt++ % Missions.values().length].getId(),
                TagRequests.random()
            );
        }
    }
}
