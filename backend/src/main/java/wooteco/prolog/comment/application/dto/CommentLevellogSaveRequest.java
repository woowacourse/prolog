package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLevellogSaveRequest {

    private Long memberId;
    private Long levelogId;
    private String content;

    public CommentLevellogSaveRequest(Long memberId, Long levelogId, String content) {
        this.memberId = memberId;
        this.levelogId = levelogId;
        this.content = content;
    }
}
