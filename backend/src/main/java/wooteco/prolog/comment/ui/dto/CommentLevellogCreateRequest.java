package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentLevellogSaveRequest;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLevellogCreateRequest {

    private String content;

    public CommentLevellogCreateRequest(final String content) {
        this.content = content;
    }

    public CommentLevellogSaveRequest toSaveRequest(final Long memberId, final Long levellogId) {
        return new CommentLevellogSaveRequest(memberId, levellogId, content);
    }
}
