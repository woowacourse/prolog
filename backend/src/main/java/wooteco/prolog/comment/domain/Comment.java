package wooteco.prolog.comment.domain;

import javax.persistence.Column;
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
import wooteco.prolog.studylog.exception.CommentDeleteException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Content content;

    @Column(nullable = false)
    private boolean isDelete;

    public Comment(final Long id,
                   final Member member,
                   final String content) {
        this.id = id;
        this.member = member;
        this.content = new Content(content);
    }

    public void update(final String content) {
        this.content = new Content(content);
    }

    public void delete() {
        if (isDelete) {
            throw new CommentDeleteException();
        }

        this.isDelete = true;
    }

    public String getContent() {
        return content.getContent();
    }
    
    public Long getMemberId() {
        return member.getId();
    }

    public String getMemberUsername() {
        return member.getUsername();
    }

    public String getMemberNickName() {
        return member.getNickname();
    }

    public String getMemberImageUrl() {
        return member.getImageUrl();
    }

    public String getMemberRole() {
        return member.getRole().name();
    }
}
