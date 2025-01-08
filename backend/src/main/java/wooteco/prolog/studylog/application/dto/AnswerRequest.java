package wooteco.prolog.studylog.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.session.domain.Answer;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerRequest {

    private Long questionId;
    private String answerContent;

    public static List<AnswerRequest> listOf(List<Answer> answers) {
        return answers.stream()
            .map(answer -> new AnswerRequest(answer.getQuestion().getId(), answer.getContent()))
            .collect(Collectors.toList());
    }
    public static List<AnswerRequest> emptyListOf() {
        return new ArrayList<>();
    }
}
