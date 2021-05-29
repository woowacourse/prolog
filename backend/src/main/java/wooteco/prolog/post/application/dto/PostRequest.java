package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.post.domain.Post;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PostRequest {
    private String title;
    private String content;
    private String category;
    private List<String> tags;

    public Post toEntity() {
        return new Post(null, title, tags, content);
    }
}