package wooteco.prolog.studylog.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentsResponse {

    private List<CommentResponse> data;

    public CommentsResponse(List<CommentResponse> data) {
        this.data = data;
    }
}
