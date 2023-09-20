package wooteco.prolog.admin.roadmap.application.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Quiz;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizResponse {

    private Long quizId;
    private String question;
    private Boolean isLearning;

    public QuizResponse(Long quizId, String question, boolean isLearning) {
        this.quizId = quizId;
        this.question = question;
        this.isLearning = isLearning;
    }

    public static QuizResponse of(Quiz quiz, boolean isLearning) {
        return new QuizResponse(quiz.getId(), quiz.getQuestion(), isLearning);
    }
}
