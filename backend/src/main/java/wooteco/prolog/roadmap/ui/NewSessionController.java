package wooteco.prolog.roadmap.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.NewSessionService;
import wooteco.prolog.roadmap.application.dto.SessionRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/curriculums/{curriculumId}/sessions")
public class NewSessionController {

    private final NewSessionService sessionService;

    @PostMapping
    public ResponseEntity<Void> createSession(@PathVariable Long curriculumId, @RequestBody SessionRequest request) {
        Long sessionId = sessionService.createSession(curriculumId, request);

        return ResponseEntity.created(URI.create("/sessions/" + sessionId)).build();
    }
}
