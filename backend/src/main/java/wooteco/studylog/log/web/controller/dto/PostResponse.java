package wooteco.studylog.log.web.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class PostResponse {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private CategoryResponse category;
    private String title;
    private String content;
    private List<String> tags;

    public PostResponse() {
    }

    public PostResponse(Long id, AuthorResponse author, LocalDateTime createdAt, CategoryResponse category, String title, String content, List<String> tags) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
        this.category = category;
        this.title = title;
        this.content = content;
        this.tags = tags;
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

    public CategoryResponse getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostResponse that = (PostResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(author, that.author) && Objects.equals(category, that.category) && Objects.equals(title, that.title) && Objects.equals(content, that.content) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, content, tags);
    }
}
