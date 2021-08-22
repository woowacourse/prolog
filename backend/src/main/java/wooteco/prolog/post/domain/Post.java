package wooteco.prolog.post.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.exception.AuthorNotValidException;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<PostTag> postTags = new ArrayList<>();

    public Post(Member member, String title, String content, Mission mission) {
        this(null, member, title, content, mission, Collections.emptyList());
    }

    public Post(Member member, String title, String content, Mission mission, List<Tag> tags) {
        this(null, member, title, content, mission, tags);
    }

    public Post(Long id, Member member, String title, String content, Mission mission, List<Tag> tags) {
        super(id);
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.mission = mission;
        addTags(new Tags(tags));
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

    public Member getMember() {
        return member;
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
