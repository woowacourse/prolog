package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Quiz;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EssayAnswerQuizResponse {

    private Long quizId;
    private String question;

    public EssayAnswerQuizResponse(Long quizId, String question) {
        this.quizId = quizId;
        this.question = question;
    }

    public static EssayAnswerQuizResponse of(Quiz quiz) {
        return new EssayAnswerQuizResponse(quiz.getId(), quiz.getQuestion());
    }
}
