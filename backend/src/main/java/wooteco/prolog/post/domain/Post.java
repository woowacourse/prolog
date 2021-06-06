package wooteco.prolog.post.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.post.application.dto.AuthorResponse;
import wooteco.prolog.tag.domain.Tag;

import java.time.LocalDateTime;
import java.util.List;

// Todo : Author, mission 도메인 객체로 변경해야 함
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {
    private Long id;
    private Member member;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Title title;
    private Content content;
    private Long missionId;
    private List<Tag> tags;

    public Post(Member member, String title, String content, Long missionId, List<Tag> tags) {
        this(null, member, null, null, new Title(title), new Content(content), missionId, tags);
    }

    public static Post of(Long id, Post post) {
        return new Post(id, post.member, post.createdAt, post.updatedAt, post.title, post.content, post.missionId, post.tags);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
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
