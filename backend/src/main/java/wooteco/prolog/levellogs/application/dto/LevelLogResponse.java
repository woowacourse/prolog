package wooteco.prolog.levellogs.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;
import wooteco.prolog.member.application.dto.MemberResponse;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class LevelLogResponse {

    private Long id;
    private String title;
    private String content;
    private MemberResponse author;
    private List<SelfDiscussionResponse> levelLogs;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LevelLogResponse(LevelLog levelLog, List<SelfDiscussion> selfDiscussions) {
        this.id = levelLog.getId();
        this.title = levelLog.getTitle();
        this.content = levelLog.getContent();
        this.author = MemberResponse.of(levelLog.getMember());
        this.levelLogs = selfDiscussions.stream()
            .map(SelfDiscussionResponse::new)
            .collect(Collectors.toList());
        this.createdAt = levelLog.getCreatedAt();
        this.updatedAt = levelLog.getUpdatedAt();
    }
}
