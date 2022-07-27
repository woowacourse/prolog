package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentChangeRequest {

    private String content;

    public CommentChangeRequest(String content) {
        this.content = content;
    }

    public CommentUpdateRequest toRequest(Long id, Long studylogId, Long commentId) {
        return new CommentUpdateRequest(id, studylogId, commentId, content);
    }
}
