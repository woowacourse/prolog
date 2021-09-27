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
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;
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
    private UpdatedContentsRepository updatedContentsRepository;

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
        studylogService.insertStudylogs(Members.BROWN.value, StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.JOANNE.value, StudylogGenerator.generate(20));
        studylogService.insertStudylogs(Members.TYCHE.value, StudylogGenerator.generate(100));

        updatedContentsRepository
            .save(new UpdatedContents(null, UpdateContent.MEMBER_TAG_UPDATE, 1));

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

    private enum Members {
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
            "조은현",
            "seovalue",
            "123456",
            "https://avatars.githubusercontent.com/u/48412963?v=4")
        );

        private final GithubProfileResponse githubProfileResponse;
        private Member value;

        Members(GithubProfileResponse githubProfileResponse) {
            this.githubProfileResponse = githubProfileResponse;
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
