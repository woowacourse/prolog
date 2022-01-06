package wooteco.prolog.studylog.domain;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.BaseEntity;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.exception.AuthorNotValidException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Studylog extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Embedded
    private StudylogTags studylogTags;

    @Embedded
    private ViewCount viewCount;

    @Embedded
    private Likes likes;

    public Studylog(Member member, String title, String content, Mission mission, List<Tag> tags) {
        this(null, member, title, content, mission, tags);
    }

    public Studylog(Long id, Member member, String title, String content, Mission mission,
                    List<Tag> tags) {
        super(id);
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.mission = mission;
        this.studylogTags = new StudylogTags();
        addTags(new Tags(tags));
        this.viewCount =  new ViewCount();
        this.likes = new Likes();
    }

    public void validateAuthor(Member member) {
        if (!this.member.equals(member)) {
            throw new AuthorNotValidException();
        }
    }

    public void update(String title, String content, Mission mission, Tags tags) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.mission = mission;
        this.studylogTags.update(convertToStudylogTags(tags));
    }

    private List<StudylogTag> convertToStudylogTags(Tags tags) {
        return tags.getList().stream()
            .map(tag -> new StudylogTag(this, tag))
            .collect(Collectors.toList());
    }

    public StudylogDocument toStudylogDocument() {
        return new StudylogDocument(
            this.getId(), this.getTitle(),
            this.getContent(), this.studylogTags.getTagIds(),
            this.mission.getId(), this.mission.getLevel().getId(),
            this.member.getUsername(), this.getUpdatedAt()
        );
    }

    public void addTags(Tags tags) {
        studylogTags.add(convertToStudylogTags(tags));
    }

    public void increaseViewCount(Member member) {
        if (!isMine(member)) {
            viewCount.increase();
        }
    }

    public void increaseViewCount() {
        viewCount.increase();
    }

    public boolean isMine(Member member) {
        return this.member.equals(member);
    }

    public void like(Long id) {
        likes.like(id);
    }

    public void unlike(Long id) {
        likes.unlike(id);
    }

    public boolean likedByMember(Long memberId) {
        return likes.likedByMember(memberId);
    }

    public int getLikeCount() {
        return likes.likeCount();
    }

    public Member getMember() {
        return member;
    }

    public Mission getMission() {
        return mission;
    }

    public List<StudylogTag> getStudylogTags() {
        return studylogTags.getValues();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public int getViewCount() {
        return viewCount.getViews();
    }
}
