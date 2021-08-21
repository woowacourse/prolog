package wooteco.prolog.post.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.exception.AuthorNotValidException;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    // Todo: createAt, updatedAt 처리
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Embedded
    private Title title;
    @Embedded
    private Content content;
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<PostTag> postTags = new ArrayList<>();

    public Post(Member member, String title, String content, Mission mission) {
        this(null, member, title, content, mission);
    }

    public Post(Long id, Member member, String title, String content, Mission mission) {
        this.id = id;
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.mission = mission;
    }

    public void validateAuthor(Member member) {
        if (this.member.isNotAuthor(member)) {
            throw new AuthorNotValidException();
        }
    }

    //Todo : TAG 중복체크
    public void addTags(Tags tags) {
        tags.toList().stream()
                .map(tag -> new PostTag(this, tag))
                .forEach(this.postTags::add);
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

    public Mission getMission() {
        return mission;
    }

    public List<PostTag> getPostTags() {
        return postTags;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void update(String title, String content, Mission mission, Tags tags) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.mission = mission;
        updatePostTags(tags);
    }

    private void updatePostTags(Tags tags) {
        List<PostTag> postTags = tags.toList().stream()
                .map(tag -> new PostTag(this, tag))
                .collect(Collectors.toList());

        removeAllPostTags();
        this.postTags.addAll(postTags);
    }

    private void removeAllPostTags() {
        this.postTags.clear();
    }

}
