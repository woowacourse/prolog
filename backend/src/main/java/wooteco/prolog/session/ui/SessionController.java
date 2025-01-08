package wooteco.prolog.session.ui;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.login.aop.MemberOnly;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionResponse> create(@RequestBody SessionRequest sessionRequest) {
        SessionResponse sessionResponse = sessionService.create(sessionRequest);
        return ResponseEntity.created(URI.create("/sessions/" + sessionResponse.getId()))
            .body(sessionResponse);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> show() {
        List<SessionResponse> responses = sessionService.findAll();
        return ResponseEntity.ok(responses);
    }

    @MemberOnly
    @GetMapping("/mine")
    public ResponseEntity<List<SessionResponse>> findMySessions(
        @AuthMemberPrincipal LoginMember member) {
        List<SessionResponse> responses = sessionService.findMySessions(member);
        return ResponseEntity.ok(responses);
    }
}
