package wooteco.prolog.roadmap.domain;

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
import wooteco.prolog.roadmap.exception.QuizNotFoundException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id", nullable = false)
    private Keyword keyword;

    @Column(nullable = false)
    private String question;

    public Quiz(final Long id, final Keyword keyword, final String question) {
        this.id = id;
        this.keyword = keyword;
        this.question = question;
    }

    public Quiz(final Keyword keyword, final String question) {
        this(null, keyword, question);
    }

    public void update(String question) {
        if (question.isEmpty()) {
            throw new QuizNotFoundException();
        }
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public String getQuestion() {
        return question;
    }
}
