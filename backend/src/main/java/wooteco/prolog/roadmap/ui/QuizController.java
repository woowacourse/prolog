package wooteco.prolog.roadmap.ui;

import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs")
public class QuizController {

    private final QuizService quizService;

    //: todo admin login 생기면 검증 추가
    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long sessionId, @PathVariable Long keywordId,
                                       @RequestBody QuizRequest quizRequest) {
        final Long quizId = quizService.createQuiz(keywordId, quizRequest);

        return ResponseEntity.created(URI.create("조회 생기면 추가")).build();
    }

    @GetMapping
    public ResponseEntity<QuizzesResponse> findQuizzesByKeyword(@PathVariable Long sessionId,
                                                                @PathVariable Long keywordId) {
        return ResponseEntity.ok(quizService.findQuizzes(keywordId));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long sessionId,
                                           @PathVariable Long keywordId,
                                           @PathVariable Long quizId) {
        quizService.deleteQuiz(keywordId);
        return ResponseEntity.noContent().build();
    }
}
