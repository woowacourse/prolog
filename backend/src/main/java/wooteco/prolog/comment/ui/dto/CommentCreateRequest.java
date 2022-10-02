package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentSaveRequest;
import wooteco.prolog.comment.domain.CommentType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentCreateRequest {

    private String content;

    public CommentCreateRequest(String content) {
        this.content = content;
    }

    public CommentSaveRequest toSaveRequest(Long postId, Long memberId, CommentType commentType) {
        return new CommentSaveRequest(postId, memberId, content, commentType);
    }
}
