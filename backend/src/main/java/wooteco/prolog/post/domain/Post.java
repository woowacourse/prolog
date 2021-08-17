package wooteco.prolog.post.domain;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import wooteco.prolog.member.domain.Member;

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
    private List<Long> tagIds;

    public Post(Member member, String title, String content, Long missionId, List<Long> tagIds) {
        this(null, member, null, null, new Title(title), new Content(content), missionId, tagIds);
    }

    public static Post of(Long id, Post post) {
        return new Post(id, post.member, post.createdAt, post.updatedAt, post.title, post.content, post.missionId, post.tagIds);
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

    public List<Long> getTagIds() {
        return tagIds;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public void addTadId(Long tagId) {
        tagIds.add(tagId);
    }
}
