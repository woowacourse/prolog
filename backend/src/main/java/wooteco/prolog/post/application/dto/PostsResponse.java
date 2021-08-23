package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostsResponse {
    private List<PostResponse> data;
    private int totalSize;
    private int totalPage;
    private int currPage;
}
