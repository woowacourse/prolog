package wooteco.prolog.levellogs.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.AuditingEntity;
import wooteco.prolog.levellogs.exception.InvalidLevelLogAuthorException;
import wooteco.prolog.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class LevelLog extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LevelLog(final Long id, final String title, final String content, final Member member) {
        this.id = id;
        this.title = new Title(title);
        this.content = new Content(content);
        this.member = member;
    }

    public LevelLog(final String title, final String content, final Member member) {
        this(null, title, content, member);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContent() {
        return content.getContent();
    }

    public Member getMember() {
        return member;
    }

    public void validateBelongTo(Long memberId) {
        if (!isBelongsTo(memberId)) {
            throw new InvalidLevelLogAuthorException();
        }
    }

    public boolean isBelongsTo(Long memberId) {
        return this.member.getId().equals(memberId);
    }

    public boolean isAuthor(Member member) {
        return this.member.equals(member);
    }

    public void update(String title, String content) {
        this.title = new Title(title);
        this.content = new Content(content);
    }
}
