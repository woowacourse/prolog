package wooteco.prolog.roadmap.ui;

import java.net.URI;
import lombok.AllArgsConstructor;
import org.elasticsearch.common.collect.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs")
public class QuizController {

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long sessionId, @PathVariable Long keywordId,
                                       @RequestBody QuizRequest quizRequest) {
        return ResponseEntity.created(URI.create("/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + 1L))
                .build();
    }

    @GetMapping
    public ResponseEntity<QuizzesResponse> findQuizzesByKeyword(@PathVariable Long sessionId,
                                                                @PathVariable Long keywordId) {
        return ResponseEntity.ok(quizzesResponse());
    }

    private QuizzesResponse quizzesResponse() {
        return new QuizzesResponse(
                1L,
                List.of(new QuizResponse(1L, "자바 8 버전에 추가된 기능들은 어떤 게 있을까요?"),
                        new QuizResponse(2L, "자바 8 버전과 11 버전의 가장 큰 차이는 무엇일까요?"),
                        new QuizResponse(3L, "자바 String은 불변 객체인가요?"))
        );
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long sessionId,
                                           @PathVariable Long keywordId,
                                           @PathVariable Long quizId) {
        return ResponseEntity.noContent().build();
    }
}
