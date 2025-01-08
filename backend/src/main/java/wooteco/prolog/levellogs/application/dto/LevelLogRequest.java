package wooteco.prolog.levellogs.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class LevelLogRequest {

    @NotNull
    private String title;
    @NotNull
    private String content;
    @NotNull
    private List<SelfDiscussionRequest> levelLogs;

    public LevelLogRequest(String title, String content,
                           List<SelfDiscussionRequest> levelLogs) {
        this.title = title;
        this.content = content;
        this.levelLogs = levelLogs;
    }
}
