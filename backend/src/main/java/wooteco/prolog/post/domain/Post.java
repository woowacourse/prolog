package wooteco.prolog.post.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.BaseEntity;
import wooteco.prolog.level.domain.Level;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.mission.domain.Mission;
import wooteco.prolog.post.exception.AuthorNotValidException;
import wooteco.prolog.posttag.domain.PostTag;
import wooteco.prolog.posttag.domain.PostTags;
import wooteco.prolog.tag.domain.Tag;
import wooteco.prolog.tag.domain.Tags;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Embedded
    private PostTags postTags;

    public Post(Member member, String title, String content, Level level, Mission mission, List<Tag> tags) {
        this(null, member, title, content, level, mission, tags);
    }

    public Post(Long id, Member member, String title, String content, Level level, Mission mission, List<Tag> tags) {
        super(id);
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.level = level;
        this.mission = mission;
        this.postTags = new PostTags();
        addTags(new Tags(tags));
    }

    public void validateAuthor(Member member) {
        if (!this.member.equals(member)) {
            throw new AuthorNotValidException();
        }
    }

    public void update(String title, String content, Level level, Mission mission, Tags tags) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.level = level;
        this.mission = mission;
        this.postTags.update(convertToPostTags(tags));
    }

    private List<PostTag> convertToPostTags(Tags tags) {
        return tags.getList().stream()
                .map(tag -> new PostTag(this, tag))
                .collect(Collectors.toList());
    }

    public void addTags(Tags tags) {
        postTags.add(convertToPostTags(tags));
    }

    public Member getMember() {
        return member;
    }

    public Level getLevel() {
        return level;
    }

    public Mission getMission() {
        return mission;
    }

    public List<PostTag> getPostTags() {
        return postTags.getValues();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }


}
