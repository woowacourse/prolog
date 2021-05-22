package wooteco.prolog.post.domain;

import wooteco.prolog.category.web.controller.dto.CategoryResponse;
import wooteco.prolog.post.web.controller.dto.AuthorResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

// Todo : Author, Category 도메인 객체로 변경해야 함
public class Post {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CategoryResponse category;
    private String title;
    private List<String> tags;
    private String content;

    public Post(Long id, AuthorResponse author, LocalDateTime createdAt, LocalDateTime updatedAt, CategoryResponse category, String title, List<String> tags, String content) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = new CategoryResponse(1L, "엄청나게 어려운 미션");
        this.title = title;
        this.tags = tags;
        this.content = content;
    }

    public Post(Long id, String title, List<String> tags, String content) {
        this(id, null, null, null, null, title, tags, content);
    }

    public Post(String title, List<String> tags, String content) {
        this(null, null, null, null, null, title, tags, content);
    }

    public static Post of(Long id, Post post) {
        return new Post(id, post.author, post.createdAt, post.updatedAt, post.category, post.title, post.tags, post.content);
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

    public CategoryResponse getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
