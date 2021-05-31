package wooteco.prolog.post.domain;

import lombok.EqualsAndHashCode;
import wooteco.prolog.post.application.dto.AuthorResponse;
import wooteco.prolog.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;

// Todo : Author, Category 도메인 객체로 변경해야 함
@EqualsAndHashCode(of = "id")
public class Post {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Title title;
    private Content content;
    private Long categoryId;
    private List<Tag> tags;

    public Post(Long id, AuthorResponse author, LocalDateTime createdAt, LocalDateTime updatedAt, Title title, Content content, Long categoryId, List<Tag> tags) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.tags = tags;
    }

    public Post(String title, String content, Long categoryId, List<Tag> tags) {
        this(null, null, null, null, new Title(title), new Content(content), categoryId, tags);
    }

    public static Post of(Long id, Post post) {
        return new Post(id, post.author, post.createdAt, post.updatedAt, post.title, post.content, post.categoryId, post.tags);
    }

    public Long getId() {
        return id;
    }

    public AuthorResponse getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getContent() {
        return content.getContent();
    }
}
