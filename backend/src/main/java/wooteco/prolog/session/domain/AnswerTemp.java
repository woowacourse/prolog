package wooteco.prolog.session.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.StudylogTemp;

@NoArgsConstructor
@Entity
@Getter
public class AnswerTemp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudylogTemp studylogTemp;
    @ManyToOne
    private Question question;
    private Long memberId;
    private String content;

    public AnswerTemp(StudylogTemp studylogTemp, Question question, Long memberId, String content) {
        this.studylogTemp = studylogTemp;
        this.question = question;
        this.memberId = memberId;
        this.content = content;
    }
}
