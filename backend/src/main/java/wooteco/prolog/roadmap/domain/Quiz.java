package wooteco.prolog.roadmap.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wooteco.prolog.common.exception.BadRequestException;

import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_QUIZ_NOT_FOUND_EXCEPTION;

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
            throw new BadRequestException(ROADMAP_QUIZ_NOT_FOUND_EXCEPTION);
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
