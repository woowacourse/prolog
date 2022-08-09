package wooteco.prolog.levellogs.application.dto;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LevelLogRequest {

    private String title;
    private String content;
    private List<SelfDiscussionRequest> levelLogs;

    public LevelLogRequest(String title, String content,
                           List<SelfDiscussionRequest> levelLogs) {
        this.title = title;
        this.content = content;
        this.levelLogs = levelLogs;
    }
}
