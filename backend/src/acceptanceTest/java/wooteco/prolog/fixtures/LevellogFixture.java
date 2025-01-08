package wooteco.prolog.fixtures;

import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevellogFixture {

    public static final List<SelfDiscussionRequest> SELF_DISCUSSION_REQUESTS = Arrays.asList(
        new SelfDiscussionRequest("Q1", "A1"),
        new SelfDiscussionRequest("Q2", "A2"));

    public static final LevelLogRequest LEVEL_LOG_REQUEST = new LevelLogRequest("title1",
        "content1",
        SELF_DISCUSSION_REQUESTS);

    public static final List<SelfDiscussionRequest> SELF_DISCUSSION_UPDATE_REQUESTS = Arrays.asList(
        new SelfDiscussionRequest("Updated Q1", "Updated A1"),
        new SelfDiscussionRequest("Updated Q2", "Updated A2"));

    public static final LevelLogRequest LEVEL_LOG_UPDATE_REQUEST = new LevelLogRequest(
        "updated title", "updated content", SELF_DISCUSSION_UPDATE_REQUESTS
    );

    public static List<LevelLogRequest> levelLogRequests() {
        List<LevelLogRequest> requests = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            requests.add(
                new LevelLogRequest("title" + i, "content" + i, selfDiscussionRequests(3)));
        }
        return requests;
    }

    private static List<SelfDiscussionRequest> selfDiscussionRequests(int count) {
        List<SelfDiscussionRequest> requests = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            requests.add(new SelfDiscussionRequest("Q" + i, "A" + i));
        }
        return requests;
    }
}
