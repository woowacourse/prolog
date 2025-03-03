package wooteco.prolog.studylog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.session.domain.AnswerFeedback;
import wooteco.prolog.session.domain.AnswerTemp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerResponse {

    private Long id;
    private String answerContent;
    private Long questionId;
    private String questionContent;
    @Nullable
    private String strengths;
    @Nullable
    private String improvementPoints;
    @Nullable
    private String additionalLearning;

    public AnswerResponse(
        final Long id,
        final String answerContent,
        final Long questionId,
        final String questionContent
    ) {
        this.id = id;
        this.answerContent = answerContent;
        this.questionId = questionId;
        this.questionContent = questionContent;
    }

    public static List<AnswerResponse> emptyListOf() {
        return new ArrayList<>();
    }

    public static List<AnswerResponse> listOf(List<Answer> answers) {
        return listOf(answers, List.of());
    }

    public static List<AnswerResponse> listOf(List<Answer> answers, List<AnswerFeedback> answerFeedbacks) {
        return listOf(answers, answerFeedbacks.stream()
            .collect(Collectors.toMap(answerFeedback -> answerFeedback.getQuestion().getId(), answerFeedback -> answerFeedback)));
    }

    public static List<AnswerResponse> listOf(List<Answer> answers, Map<Long, AnswerFeedback> answerFeedbacks) {
        if (answers == null || answers.isEmpty()) {
            return emptyListOf();
        }

        return answers.stream()
            .map(answer -> of(answer, answerFeedbacks.get(answer.getQuestion().getId())))
            .collect(Collectors.toList());
    }

    public static AnswerResponse of(AnswerTemp answerTemp) {
        return new AnswerResponse(answerTemp.getId(), answerTemp.getContent(), answerTemp.getQuestion().getId(),
            answerTemp.getQuestion().getContent());
    }

    public static AnswerResponse of(Answer answer) {
        return new AnswerResponse(answer.getId(), answer.getContent(), answer.getQuestion().getId(),
            answer.getQuestion().getContent());
    }

    public static AnswerResponse of(Answer answer, AnswerFeedback answerFeedback) {
        if (answerFeedback == null) {
            return of(answer);
        }
        return new AnswerResponse(answer.getId(), answer.getContent(), answer.getQuestion().getId(),
            answer.getQuestion().getContent(), answerFeedback.getStrengths(), answerFeedback.getImprovementPoints(), answerFeedback.getAdditionalLearning());
    }
}
