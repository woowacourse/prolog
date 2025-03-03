package wooteco.prolog.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "answer_feedback")
public class AnswerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    private Long memberId;

    @Embedded
    private QnaFeedbackRequest request;

    @Embedded
    private QnaFeedbackContents contents;

    @Column
    private boolean visible;

    public AnswerFeedback(
        final Question question,
        final Long memberId,
        final QnaFeedbackRequest request,
        final QnaFeedbackContents contents
    ) {
        this(question, memberId, request, contents, false);
    }

    public AnswerFeedback(
        final Question question,
        final Long memberId,
        final QnaFeedbackRequest request,
        final QnaFeedbackContents contents,
        final boolean visible
    ) {
        this.question = question;
        this.memberId = memberId;
        this.request = request;
        this.contents = contents;
        this.visible = visible;
    }

    public String getStrengths() {
        return contents.strengths();
    }

    public String getImprovementPoints() {
        return contents.improvementPoints();
    }

    public String getAdditionalLearning() {
        return contents.additionalLearning();
    }
}
