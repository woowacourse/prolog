package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentUpdateRequest;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentChangeRequest {

    private String content;

    public CommentChangeRequest(String content) {
        this.content = content;
    }

    public CommentUpdateRequest toUpdateRequest(Long memberId,
                                                Long commentId) {
        return new CommentUpdateRequest(memberId, commentId, content);
    }
}
