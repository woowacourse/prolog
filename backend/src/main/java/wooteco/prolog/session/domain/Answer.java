package wooteco.prolog.session.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    public boolean updateContent(String newContent) {
        if (this.content.equals(newContent)) {
            return false;
        }

        this.content = newContent;
        return true;
    }
}
