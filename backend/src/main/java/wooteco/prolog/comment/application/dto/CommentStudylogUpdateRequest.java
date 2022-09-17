package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentStudylogUpdateRequest {

    private Long memberId;
    private Long studylogId;
    private Long commentStudylogId;
    private String content;

    public CommentStudylogUpdateRequest(final Long memberId,
                                        final Long studylogId,
                                        final Long commentStudylogId,
                                        final String content) {
        this.memberId = memberId;
        this.studylogId = studylogId;
        this.commentStudylogId = commentStudylogId;
        this.content = content;
    }
}
