package wooteco.prolog.studylog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.AuditingEntity;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;

import java.util.Objects;

import static wooteco.prolog.common.exception.BadRequestCode.COMMENT_DELETE_EXCEPTION;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studylog_id")
    private Studylog studylog;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private boolean isDelete;

    public Comment(Long id, Member member, Studylog studylog, String content) {
        this.id = id;
        this.member = member;
        this.studylog = studylog;
        this.content = Objects.requireNonNull(content);
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void delete() {
        if (isDelete) {
            throw new BadRequestException(COMMENT_DELETE_EXCEPTION);
        }

        this.isDelete = true;
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
