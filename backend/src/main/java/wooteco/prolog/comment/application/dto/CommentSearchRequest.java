package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.domain.CommentType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentSearchRequest {

    private Long postId;
    private CommentType commentType;

    public CommentSearchRequest(final Long postId, final CommentType commentType) {
        this.postId = postId;
        this.commentType = commentType;
    }
}
