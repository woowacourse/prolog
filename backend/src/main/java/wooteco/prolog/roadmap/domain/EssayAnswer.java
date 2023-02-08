package wooteco.prolog.roadmap.domain;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import wooteco.prolog.common.AuditingEntity;
import wooteco.prolog.member.domain.Member;

@Table(name = "essay_answer")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EssayAnswer extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "text")
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private EssayAnswer(final Long id, final Quiz quiz, final String answer, Member member) {
        this.id = id;
        this.quiz = quiz;
        this.answer = answer;
        this.member = member;
    }

    public EssayAnswer(final Quiz quiz, final String answer, Member member) {
        this(null, quiz, answer, member);
    }

    public void update(String answer, Member member) {
        if (StringUtils.isBlank(answer)) {
            throw new IllegalArgumentException("답변 내용은 공백일 수 없습니다.");
        }
        if (!this.member.equals(member)) {
            throw new IllegalArgumentException("본인이 작성한 답변만 수정할 수 있습니다.");
        }

        this.answer = answer;
    }

}
