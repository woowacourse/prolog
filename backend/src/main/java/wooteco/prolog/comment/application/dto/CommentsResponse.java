package wooteco.prolog.comment.application.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentsResponse {

    private List<CommentResponse> data;

    public CommentsResponse(final List<CommentResponse> data) {
        this.data = data;
    }
}
