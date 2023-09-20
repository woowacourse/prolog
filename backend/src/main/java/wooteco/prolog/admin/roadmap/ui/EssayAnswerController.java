package wooteco.prolog.admin.roadmap.ui;

import static java.util.stream.Collectors.toList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.admin.roadmap.application.EssayAnswerService;
import wooteco.prolog.admin.roadmap.application.QuizService;
import wooteco.prolog.admin.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.admin.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.admin.roadmap.application.dto.EssayAnswerSearchRequest;
import wooteco.prolog.admin.roadmap.application.dto.EssayAnswerUpdateRequest;
import wooteco.prolog.admin.roadmap.application.dto.QuizResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;
import wooteco.prolog.studylog.application.dto.EssayAnswersResponse;


@RestController
@RequestMapping
public class EssayAnswerController {

    private final EssayAnswerService essayAnswerService;
    private final QuizService quizService;

    @Autowired
    public EssayAnswerController(EssayAnswerService essayAnswerService,
                                 QuizService quizService) {
        this.essayAnswerService = essayAnswerService;
        this.quizService = quizService;
    }

    @PostMapping("/essay-answers")
    public ResponseEntity<Long> create(@RequestBody EssayAnswerRequest request,
                                       @AuthMemberPrincipal LoginMember member) {

        return ResponseEntity.ok(essayAnswerService.createEssayAnswer(request, member.getId()));
    }

    @GetMapping("/essay-answers")
    public ResponseEntity<EssayAnswersResponse> search(
        EssayAnswerSearchRequest request,
        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(essayAnswerService.searchEssayAnswers(request, pageable));
    }

    @GetMapping("/essay-answers/{essayAnswerId}")
    public ResponseEntity<EssayAnswerResponse> findById(@PathVariable Long essayAnswerId) {
        EssayAnswer essayAnswer = essayAnswerService.getById(essayAnswerId);
        EssayAnswerResponse response = EssayAnswerResponse.of(essayAnswer);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/essay-answers/{essayAnswerId}")
    public ResponseEntity<Void> updateById(@PathVariable Long essayAnswerId,
                                           @AuthMemberPrincipal LoginMember member,
                                           @RequestBody EssayAnswerUpdateRequest request) {
        essayAnswerService.updateEssayAnswer(essayAnswerId, request, member.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/essay-answers/{essayAnswerId}")
    public ResponseEntity<Void> deleteEssayAnswerById(@PathVariable Long essayAnswerId,
                                                      @AuthMemberPrincipal LoginMember member) {
        essayAnswerService.deleteEssayAnswer(essayAnswerId, member.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quizzes/{quizId}")
    public ResponseEntity<QuizResponse> findQuizById(@PathVariable Long quizId,
                                                     @AuthMemberPrincipal LoginMember member) {
        return ResponseEntity.ok(quizService.findById(quizId, member.getId()));
    }

    @GetMapping("/quizzes/{quizId}/essay-answers")
    public ResponseEntity<List<EssayAnswerResponse>> findAnswersByQuizId(
        @PathVariable Long quizId) {

        List<EssayAnswer> essayAnswers = essayAnswerService.findByQuizId(quizId);
        List<EssayAnswerResponse> responses = essayAnswers.stream().map(EssayAnswerResponse::of)
            .collect(toList());

        return ResponseEntity.ok(responses);
    }

}
