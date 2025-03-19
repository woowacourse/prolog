package wooteco.prolog.roadmap.domain;

import static wooteco.prolog.common.exception.BadRequestCode.ESSAY_ANSWER_NOT_VALID_USER;
import static wooteco.prolog.common.exception.BadRequestCode.NOT_EMPTY_ESSAY_ANSWER_EXCEPTION;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import wooteco.prolog.common.entity.AuditingEntity;
import wooteco.prolog.common.exception.BadRequestException;
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
            throw new BadRequestException(NOT_EMPTY_ESSAY_ANSWER_EXCEPTION);
        }
        if (!this.member.equals(member)) {
            throw new BadRequestException(ESSAY_ANSWER_NOT_VALID_USER);
        }

        this.answer = answer;
    }

}
