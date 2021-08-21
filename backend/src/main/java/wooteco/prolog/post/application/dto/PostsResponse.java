package wooteco.prolog.post.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostsResponse {
    private List<PostResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;
}
