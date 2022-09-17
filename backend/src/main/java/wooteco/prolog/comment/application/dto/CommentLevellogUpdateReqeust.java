package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentLevellogUpdateReqeust {

    private Long memberId;
    private Long levellogId;
    private Long commentLevellogId;
    private String content;

    public CommentLevellogUpdateReqeust(final Long memberId,
                                        final Long levellogId,
                                        final Long commentLevellogId,
                                        final String content) {
        this.memberId = memberId;
        this.levellogId = levellogId;
        this.commentLevellogId = commentLevellogId;
        this.content = content;
    }
}
