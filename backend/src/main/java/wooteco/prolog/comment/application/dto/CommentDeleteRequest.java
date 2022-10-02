package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentDeleteRequest {

    private Long memberId;
    private Long commentId;

    public CommentDeleteRequest(final Long memberId, final Long commentId) {
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
