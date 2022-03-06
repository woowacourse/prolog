package wooteco.prolog.session.ui;

import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.session.application.SessionService;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;

@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody SessionRequest sessionRequest) {
        SessionResponse sessionResponse = sessionService.create(sessionRequest);
        return ResponseEntity.created(URI.create("/sessions/" + sessionResponse.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> show() {
        List<SessionResponse> responses = sessionService.findAll();
        return ResponseEntity.ok(responses);
    }
}
