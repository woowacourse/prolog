package wooteco.prolog.roadmap.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EssayAnswerRequest {

    private Long quizId;
    private String answer;
}
