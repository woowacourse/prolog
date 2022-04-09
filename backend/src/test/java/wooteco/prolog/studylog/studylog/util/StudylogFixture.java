package wooteco.prolog.studylog.studylog.util;

import static java.util.EnumSet.allOf;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.MissionService;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;
import wooteco.prolog.studylog.domain.Tags;

public enum StudylogFixture {
    자동차_미션_정리("자동차 미션 정리", "부릉 부릉 자동차가 나가신다 부릉부릉", "자동차 미션", "임파시블세션", Tags.of(
        Arrays.asList("자동차", "전략 패턴", "부릉"))),
    로또_미션_정리("로또 미션 정리", "따르르릉 로또", "로또 미션", "임파시블세션", Tags.of(Arrays.asList("로또", "TDD", "랜덤")));

    @Component
    public static class InjectHelper {

        @Autowired
        private MissionService missionService;
        @Autowired
        private SessionService sessionService;

        @PostConstruct
        void init() {
            allOf(StudylogFixture.class)
                .forEach(container -> container.injectMissionService(missionService, sessionService));
        }
    }

    private String title;
    private String content;
    private String missionName;
    private String sessionName;
    private Tags tags;
    private MissionService missionService;
    private SessionService sessionService;

    StudylogFixture(String title, String content, String missionName, String sessionName, Tags tags) {
        this.title = title;
        this.content = content;
        this.missionName = missionName;
        this.sessionName = sessionName;
        this.tags = tags;
    }

    private void injectMissionService(MissionService missionService, SessionService sessionService) {
        this.missionService = missionService;
        this.sessionService = sessionService;
    }

    public StudylogRequest asRequestWithTags(List<TagRequest> tagRequests) {
        final MissionResponse missionResponse = createMission(missionName, sessionName);
        return new StudylogRequest(title, content, missionResponse.getId(), tagRequests);
    }

    public StudylogRequest asRequest() {
        final List<TagRequest> tagRequests = tags.getList().stream()
            .map(tag -> new TagRequest(tag.getName()))
            .collect(Collectors.toList());

        return asRequestWithTags(tagRequests);
    }

    private MissionResponse createMission(String missionName, String sessionName) {
        final SessionResponse sessionResponse = sessionService.findAll().stream()
            .filter(lr -> lr.getName().equals(sessionName))
            .findAny().orElseGet(() -> sessionService.create(new SessionRequest(sessionName)));
        return missionService.create(new MissionRequest(missionName, sessionResponse.getId()));
    }
}
