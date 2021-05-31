package wooteco.prolog.post.application.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.post.domain.Post;
import wooteco.prolog.tag.dto.TagResponse;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PostResponse {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryResponse category;
    private String title;
    private String content;
    private List<TagResponse> tags;

    public PostResponse(Post post, CategoryResponse categoryResponse, List<TagResponse> tagResponses) {
        this(
                post.getId(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                categoryResponse,
                post.getTitle(),
                post.getContent(),
                tagResponses);
    }
}