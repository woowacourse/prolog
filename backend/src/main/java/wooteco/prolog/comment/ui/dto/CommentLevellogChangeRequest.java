package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentLevellogUpdateReqeust;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLevellogChangeRequest {

    private String content;

    public CommentLevellogChangeRequest(final String content) {
        this.content = content;
    }

    public CommentLevellogUpdateReqeust toUpdateRequest(final Long id,
                                                        final Long levellogId,
                                                        final Long commentLevellogId) {
        return new CommentLevellogUpdateReqeust(id, levellogId, commentLevellogId, content);
    }
}
