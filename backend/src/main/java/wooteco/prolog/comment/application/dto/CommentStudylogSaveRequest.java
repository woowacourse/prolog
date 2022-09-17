package wooteco.prolog.comment.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentStudylogSaveRequest {

    private Long memberId;
    private Long studylogId;
    private String content;

    public CommentStudylogSaveRequest(final Long memberId,
                                      final Long studylogId,
                                      final String content) {
        this.memberId = memberId;
        this.studylogId = studylogId;
        this.content = content;
    }
}
