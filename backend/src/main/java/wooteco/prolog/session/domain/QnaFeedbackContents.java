package wooteco.prolog.session.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record QnaFeedbackContents(
    @Column(nullable = false, length = 1024)
    String strengths,

    @Column(nullable = false, length = 1024)
    String improvementPoints,

    @Column(nullable = false, length = 1024)
    String additionalLearning,

    @Column(nullable = false)
    int score
) {

}
