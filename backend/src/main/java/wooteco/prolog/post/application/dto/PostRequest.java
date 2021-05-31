package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.domain.Tag;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PostRequest {
    private String title;
    private String content;
    private Long categoryId;
    private List<TagRequest> tags;

    public Post toEntity() {
        List<Tag> tagEntities = tags.stream()
                .map(TagRequest::toEntity)
                .collect(Collectors.toList());

        return new Post(title, content, categoryId, tagEntities);
    }
}