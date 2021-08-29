package wooteco.prolog.post.util;

import static java.util.EnumSet.allOf;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wooteco.prolog.level.application.LevelService;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.mission.application.MissionService;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.domain.Tags;
import wooteco.prolog.tag.dto.TagRequest;

public enum PostFixture {

    자동차_미션_정리("자동차 미션 정리", "부릉 부릉 자동차가 나가신다 부릉부릉", "자동차 미션", "임파시블레벨", Tags.of(Arrays.asList("자동차", "전략 패턴", "부릉"))),
    로또_미션_정리("로또 미션 정리", "따르르릉 로또", "로또 미션", "임파시블레벨",Tags.of(Arrays.asList("로또", "TDD", "랜덤")));

    @Component
    public static class InjectHelper {
        @Autowired
        private MissionService missionService;
        @Autowired
        private LevelService levelService;

        @PostConstruct
        void init() {
            allOf(PostFixture.class)
                    .forEach(container -> container.injectMissionService(missionService, levelService));
        }
    }

    private String title;
    private String content;
    private String missionName;
    private String levelName;
    private Tags tags;
    private MissionService missionService;
    private LevelService levelService;

    PostFixture(String title, String content, String missionName, String levelName, Tags tags) {
        this.title = title;
        this.content = content;
        this.missionName = missionName;
        this.levelName = levelName;
        this.tags = tags;
    }

    private void injectMissionService(MissionService missionService, LevelService levelService) {
        this.missionService = missionService;
        this.levelService = levelService;
    }

    public PostRequest asRequestWithTags(List<TagRequest> tagRequests) {
        final MissionResponse missionResponse = createMission(missionName, levelName);
        return new PostRequest(title, content, missionResponse.getId(), tagRequests);
    }

    public PostRequest asRequest() {
        final List<TagRequest> tagRequests = tags.getList().stream()
                .map(tag -> new TagRequest(tag.getName()))
                .collect(Collectors.toList());

        return asRequestWithTags(tagRequests);
    }

    private MissionResponse createMission(String missionName, String levelName) {
        final LevelResponse levelResponse = levelService.findAll().stream()
                .filter(lr -> lr.getName().equals(levelName))
                .findAny().orElseGet(() -> levelService.create(new LevelRequest(levelName)));
        return missionService.create(new MissionRequest(missionName, levelResponse.getId()));
    }
}
