package wooteco.prolog.session.application.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MissionQuestionResponse {

    private Long missionId;
    private List<QuestionResponse> questions;
}
