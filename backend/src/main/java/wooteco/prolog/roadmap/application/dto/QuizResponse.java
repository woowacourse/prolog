package wooteco.prolog.roadmap.application.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Quiz;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizResponse {

    private Long quizId;
    private String question;

    public QuizResponse(Long quizId, String question) {
        this.quizId = quizId;
        this.question = question;
    }

    public static QuizResponse of(Quiz quiz) {
        return new QuizResponse(quiz.getId(), quiz.getQuestion());
    }
}
