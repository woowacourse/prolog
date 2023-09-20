package wooteco.prolog.admin.roadmap.ui;

import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.admin.roadmap.application.AdminQuizService;
import wooteco.prolog.admin.roadmap.application.dto.QuizRequest;
import wooteco.prolog.admin.roadmap.application.dto.QuizzesResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/admin/sessions/{sessionId}/keywords/{keywordId}/quizs")
public class AdminQuizController {

    private final AdminQuizService adminQuizService;

    @GetMapping
    public ResponseEntity<QuizzesResponse> findQuizzesByKeyword(@PathVariable Long sessionId,
        @PathVariable Long keywordId,
        @AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(adminQuizService.findQuizzesByKeywordId(keywordId, member.getId()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@PathVariable Long sessionId, @PathVariable Long keywordId,
                                       @RequestBody QuizRequest quizRequest) {
        final Long quizId = adminQuizService.createQuiz(keywordId, quizRequest);

        return ResponseEntity.created(
                URI.create("/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + quizId))
            .build();
    }

    @PutMapping("/{quizId}")
    ResponseEntity<Void> updateQuiz(@PathVariable Long sessionId,
                                    @PathVariable Long keywordId,
                                    @PathVariable Long quizId,
                                    @RequestBody QuizRequest quizRequest) {
        adminQuizService.updateQuiz(quizId, quizRequest);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long sessionId,
                                           @PathVariable Long keywordId,
                                           @PathVariable Long quizId) {
        adminQuizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}
