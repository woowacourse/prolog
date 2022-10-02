package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.domain.CommentType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentSaveRequest {

    private Long postId;
    private Long memberId;
    private String content;
    private CommentType commentType;

    public CommentSaveRequest(final Long postId,
                              final Long memberId,
                              final String content,
                              final CommentType commentType) {
        this.postId = postId;
        this.memberId = memberId;
        this.content = content;
        this.commentType = commentType;
    }
}
