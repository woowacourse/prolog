package wooteco.prolog.studylog.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Answer;
import wooteco.prolog.session.domain.AnswerTemp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerResponse {

    private Long id;
    private String answerContent;
    private Long questionId;
    private String questionContent;

    public static List<AnswerResponse> emptyListOf() {
        return new ArrayList<>();
    }

    public static List<AnswerResponse> listOf(List<Answer> answers) {
        if (answers == null || answers.isEmpty()) {
            return emptyListOf();
        }

        return answers.stream()
            .map(answer -> new AnswerResponse(answer.getId(), answer.getContent(), answer.getQuestion().getId(),
                answer.getQuestion().getContent()))
            .collect(Collectors.toList());
    }

    public static AnswerResponse of(AnswerTemp answerTemp) {
        return new AnswerResponse(answerTemp.getId(), answerTemp.getContent(), answerTemp.getQuestion().getId(),
            answerTemp.getQuestion().getContent());
    }
}
