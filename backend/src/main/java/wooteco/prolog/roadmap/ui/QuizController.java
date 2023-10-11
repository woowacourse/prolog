package wooteco.prolog.roadmap.ui;

import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.roadmap.application.QuizService;
import wooteco.prolog.roadmap.application.dto.QuizRequest;
import wooteco.prolog.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.application.dto.QuizzesResponse;

@RestController
@AllArgsConstructor
public class QuizController {

    private final QuizService quizService;

    //: todo admin login 생기면 검증 추가
    @PostMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs")
    public ResponseEntity<Void> create(@PathVariable Long sessionId, @PathVariable Long keywordId,
        @RequestBody QuizRequest quizRequest) {
        final Long quizId = quizService.createQuiz(keywordId, quizRequest);

        return ResponseEntity.created(
                URI.create("/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + quizId))
            .build();
    }

    @GetMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs/{quizId}")
    public ResponseEntity<QuizResponse> findQuizById(@PathVariable Long quizId,
        @AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(quizService.findById(quizId, member.getId()));
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> newFindQuizById(
        @PathVariable Long quizId,
        @AuthMemberPrincipal LoginMember member
    ) {
        return ResponseEntity.ok(quizService.findById(quizId, member.getId()));
    }

    @GetMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs")
    public ResponseEntity<QuizzesResponse> findQuizzesByKeyword(@PathVariable Long sessionId,
        @PathVariable Long keywordId,
        @AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(quizService.findQuizzesByKeywordId(keywordId, member.getId()));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<QuizzesResponse> newFindQuizzesByKeyword(
        @RequestParam("keywordId") Long keywordId,
        @AuthMemberPrincipal LoginMember member
    ) {
        return ResponseEntity.ok(quizService.findQuizzesByKeywordId(keywordId, member.getId()));
    }

    @PutMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs/{quizId}")
    ResponseEntity<Void> updateQuiz(@PathVariable Long sessionId,
        @PathVariable Long keywordId,
        @PathVariable Long quizId,
        @RequestBody QuizRequest quizRequest) {
        quizService.updateQuiz(quizId, quizRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/sessions/{sessionId}/keywords/{keywordId}/quizs/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long sessionId,
        @PathVariable Long keywordId,
        @PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}
