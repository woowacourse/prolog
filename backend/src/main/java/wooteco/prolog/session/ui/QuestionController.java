package wooteco.prolog.session.ui;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.session.application.QuestionService;
import wooteco.prolog.session.application.dto.MissionQuestionResponse;
import wooteco.prolog.session.application.dto.QuestionResponse;
import wooteco.prolog.session.domain.Question;

@RestController
@AllArgsConstructor
public class QuestionController {

    private QuestionService questionService;

    @GetMapping("/questions")
    public ResponseEntity<MissionQuestionResponse> questions(@RequestParam Long missionId) {

        List<Question> questions = questionService.findQuestionsByMissionId(missionId);
        List<QuestionResponse> questionResponses = questions.stream()
            .map(it -> new QuestionResponse(it.getId(), it.getContent()))
            .collect(Collectors.toList());

        MissionQuestionResponse missionQuestionResponse = new MissionQuestionResponse(missionId, questionResponses);
        return ResponseEntity.ok(missionQuestionResponse);
    }
}
