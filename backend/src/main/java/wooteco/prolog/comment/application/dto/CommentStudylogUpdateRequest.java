package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentStudylogUpdateRequest {

    private Long memberId;
    private Long studylogId;
    private Long studylogCommentId;
    private String content;

    public CommentStudylogUpdateRequest(Long memberId,
                                Long studylogId,
                                Long studylogCommentId,
                                String content) {
        this.memberId = memberId;
        this.studylogId = studylogId;
        this.studylogCommentId = studylogCommentId;
        this.content = content;
    }
}
