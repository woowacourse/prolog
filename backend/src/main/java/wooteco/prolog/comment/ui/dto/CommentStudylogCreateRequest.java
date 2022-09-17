package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentStudylogSaveRequest;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentStudylogCreateRequest {

    private String content;

    public CommentStudylogCreateRequest(String content) {
        this.content = content;
    }

    public CommentStudylogSaveRequest toSaveRequest(Long memberId, Long studylogId) {
        return new CommentStudylogSaveRequest(memberId, studylogId, content);
    }
}
