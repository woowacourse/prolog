package wooteco.prolog.comment.ui.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.comment.application.dto.CommentStudylogUpdateRequest;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentStudylogChangeRequest {

    private String content;

    public CommentStudylogChangeRequest(String content) {
        this.content = content;
    }

    public CommentStudylogUpdateRequest toUpdateRequest(Long id,
                                                        Long studylogId,
                                                        Long studylogCommentId) {
        return new CommentStudylogUpdateRequest(id, studylogId, studylogCommentId, content);
    }
}
