package wooteco.prolog.roadmap.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.roadmap.application.EssayAnswerService;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerResponse;
import wooteco.prolog.roadmap.domain.EssayAnswer;

@RestController
@RequestMapping("/essay-answers")
public class EssayAnswerController {

    private final EssayAnswerService essayAnswerService;

    @Autowired
    public EssayAnswerController(EssayAnswerService essayAnswerService) {
        this.essayAnswerService = essayAnswerService;
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody EssayAnswerRequest request,
                                       @AuthMemberPrincipal LoginMember member) {

        return ResponseEntity.ok(essayAnswerService.createEssayAnswer(request, member.getId()));
    }

    @GetMapping("/{essayAnswerId}")
    public ResponseEntity<EssayAnswerResponse> findById(@PathVariable Long essayAnswerId) {
        EssayAnswer essayAnswer = essayAnswerService.getById(essayAnswerId);
        EssayAnswerResponse response = EssayAnswerResponse.of(essayAnswer);
        return ResponseEntity.ok(response);
    }

}
