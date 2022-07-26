package wooteco.prolog.studylog.application.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequest {

    private Long memberId;
    private Long studylogId;
    private Long commentId;
    private String content;

    public CommentUpdateRequest(Long memberId, Long studylogId, Long commentId, String content) {
        this.memberId = memberId;
        this.studylogId = studylogId;
        this.commentId = commentId;
        this.content = content;
    }
}
