package wooteco.prolog.studylog.domain;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.AuditingEntity;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.exception.AuthorNotValidException;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Studylog extends AuditingEntity {

    private static final String DELETED_TITLE = "삭제된 학습로그";
    private static final String DELETED_CONTENT = "삭제된 학습로그입니다.";
    private static final int POPULAR_SCORE = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @Embedded
    private StudylogTags studylogTags;

    @Embedded
    private ViewCount viewCount;

    @Embedded
    private Likes likes;

    private boolean deleted;

    public Studylog(Member member, String title, String content, Session session, Mission mission, List<Tag> tags) {
        this.member = member;
        this.title = new Title(title);
        this.content = new Content(content);
        this.studylogTags = new StudylogTags();
        this.session = session;
        this.mission = mission;
        addTags(new Tags(tags));
        this.viewCount = new ViewCount();
        this.likes = new Likes();
    }

    public Studylog(Member member, String title, String content, Mission mission, List<Tag> tags) {
        this(member, title, content, mission.getSession(), mission, tags);
    }

    public void validateBelongTo(Long memberId) {
        if (!isBelongsTo(memberId)) {
            throw new AuthorNotValidException();
        }
    }

    public boolean isBelongsTo(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public void update(String title, String content, Tags tags) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.studylogTags.update(convertToStudylogTags(tags));
    }

    public void update(String title, String content, Session session, Mission mission, Tags tags) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.session = session;
        this.mission = mission;
        this.studylogTags.update(convertToStudylogTags(tags));
    }

    public StudylogDocument toStudylogDocument() {
        return new StudylogDocument(
            this.getId(), this.getTitle(),
            this.getContent(), this.studylogTags.getTagIds(),
            null, null,
            this.member.getUsername(), this.getUpdatedAt()
        );
    }

    public void addTags(Tags tags) {
        studylogTags.add(convertToStudylogTags(tags));
    }

    private List<StudylogTag> convertToStudylogTags(Tags tags) {
        return tags.getList().stream()
                .map(tag -> new StudylogTag(this, tag))
                .collect(Collectors.toList());
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
        likes.like(this, id);
    }

    public void unlike(Long id) {
        likes.unlike(this, id);
    }

    public boolean likedByMember(Long memberId) {
        return likes.likedByMember(memberId);
    }

    public int getPopularScore() {
        return (getLikeCount() * POPULAR_SCORE) + getViewCount();
    }

    public int getLikeCount() {
        return likes.likeCount();
    }

    public Member getMember() {
        return member;
    }

    public List<StudylogTag> getStudylogTags() {
        return studylogTags.getValues();
    }

    public String getTitle() {
        if (deleted) {
            return DELETED_TITLE;
        }
        return title.getTitle();
    }

    public String getContent() {
        if (deleted) {
            return DELETED_CONTENT;
        }
        return content.getContent();
    }

    public int getViewCount() {
        return viewCount.getViews();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    public void updateSession(Session session) {
        this.session = session;
    }

    public void updateMission(Mission mission) {
        this.mission = mission;
    }

    public boolean isContainsCurriculum(Curriculum curriculum) {
        return this.session.isSameAs(curriculum);
    }
}
