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

    public CommentLevellogUpdateReqeust(Long memberId,
                                        Long levellogId,
                                        Long commentLevellogId,
                                        String content) {
        this.memberId = memberId;
        this.levellogId = levellogId;
        this.commentLevellogId = commentLevellogId;
        this.content = content;
    }
}
