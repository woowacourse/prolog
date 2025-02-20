package wooteco.prolog.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record QnaFeedbackRequest(
    @Column(nullable = false, length = 1024)
    String goal,

    @Column(nullable = false, length = 1024)
    String question,

    @Column(nullable = false, length = 1024)
    String answer
) {
}
