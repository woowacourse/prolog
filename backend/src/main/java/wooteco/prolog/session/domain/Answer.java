package wooteco.prolog.session.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.studylog.domain.Studylog;

@NoArgsConstructor
@Entity
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Studylog studylog;
    @ManyToOne
    private Question question;
    private Long memberId;
    private String content;

    public Answer(Studylog studylog, Question question, Long memberId, String content) {
        this.studylog = studylog;
        this.question = question;
        this.memberId = memberId;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
