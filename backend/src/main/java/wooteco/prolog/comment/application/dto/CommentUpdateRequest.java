package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentUpdateRequest {

    private Long memberId;
    private Long commentId;
    private String content;

    public CommentUpdateRequest(final Long memberId, final Long commentId, final String content) {
        this.memberId = memberId;
        this.commentId = commentId;
        this.content = content;
    }
}
