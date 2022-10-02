package wooteco.prolog.comment.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Content content;

    @Column(nullable = false)
    private boolean isDelete;

    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    public Comment(final Long id,
                   final Member member,
                   final String content) {
        this.id = id;
        this.member = member;
        this.content = new Content(content);
    }

    public Comment(final Long id,
                   final Long postId,
                   final Member member,
                   final Content content,
                   final boolean isDelete,
                   final CommentType commentType) {
        this.id = id;
        this.postId = postId;
        this.member = member;
        this.content = content;
        this.isDelete = isDelete;
        this.commentType = commentType;
    }

    public static Comment createComment(final Long postId,
                                        final Member member,
                                        final Content content,
                                        final CommentType commentType) {
        return new Comment(null, postId, member, content, false, commentType);
    }

    public void update(final Content content) {
        this.content = content;
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
