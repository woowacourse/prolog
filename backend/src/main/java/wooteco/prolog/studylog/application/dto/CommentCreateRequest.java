package wooteco.prolog.studylog.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentCreateRequest {

    private String content;

    public CommentCreateRequest(String content) {
        this.content = content;
    }

    public CommentSaveRequest toSaveRequest(Long memberId, Long studylogId) {
        return new CommentSaveRequest(memberId, studylogId, content);
    }
}
