package wooteco.prolog.levellogs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.AuditingEntity;
import wooteco.prolog.member.domain.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelLog extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LevelLog(final String title, final String content, final Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }
}
