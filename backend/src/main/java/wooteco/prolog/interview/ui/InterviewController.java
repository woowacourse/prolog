package wooteco.prolog.interview.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.interview.application.InterviewAnswerRequest;
import wooteco.prolog.interview.application.InterviewService;
import wooteco.prolog.interview.application.InterviewSessionRequest;
import wooteco.prolog.interview.application.InterviewSessionResponse;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    InterviewController(final InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewSessionResponse> create(
        @AuthMemberPrincipal final LoginMember member,
        @RequestBody final InterviewSessionRequest request
    ) {
        final var session = interviewService.createSession(
            member.getId(),
            request
        );

        return ResponseEntity.created(URI.create("/interviews/" + session.id()))
            .body(session);
    }

    @PostMapping("/{sessionId}/answers")
    public ResponseEntity<InterviewSessionResponse> answer(
        @AuthMemberPrincipal final LoginMember member,
        @PathVariable final Long sessionId,
        @RequestBody final InterviewAnswerRequest request
    ) {
        final var session = interviewService.answer(
            member.getId(),
            sessionId,
            request
        );

        return ResponseEntity.ok(session);
    }
}
