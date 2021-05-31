package wooteco.prolog.post.domain;

import lombok.EqualsAndHashCode;
import wooteco.prolog.post.application.dto.AuthorResponse;
import wooteco.prolog.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;

// Todo : Author, mission 도메인 객체로 변경해야 함
@EqualsAndHashCode(of = "id")
public class Post {
    private Long id;
    private AuthorResponse author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Title title;
    private Content content;
    private Long missionId;
    private List<Tag> tags;

    public Post(Long id, AuthorResponse author, LocalDateTime createdAt, LocalDateTime updatedAt, Title title, Content content, Long missionId, List<Tag> tags) {
        this.id = id;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.title = title;
        this.content = content;
        this.missionId = missionId;
        this.tags = tags;
    }

    public Post(String title, String content, Long missionId, List<Tag> tags) {
        this(null, null, null, null, new Title(title), new Content(content), missionId, tags);
    }

    public static Post of(Long id, Post post) {
        return new Post(id, post.author, post.createdAt, post.updatedAt, post.title, post.content, post.missionId, post.tags);
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

    public Long getMissionId() {
        return missionId;
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
