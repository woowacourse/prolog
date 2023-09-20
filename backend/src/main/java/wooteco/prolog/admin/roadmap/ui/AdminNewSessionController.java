package wooteco.prolog.admin.roadmap.ui;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.admin.roadmap.application.AdminNewSessionService;
import wooteco.prolog.admin.roadmap.application.dto.SessionRequest;
import wooteco.prolog.admin.roadmap.application.dto.SessionsResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/curriculums/{curriculumId}/sessions")
public class AdminNewSessionController {

    private final AdminNewSessionService sessionService;

    @GetMapping
    public ResponseEntity<SessionsResponse> findSessions(@PathVariable Long curriculumId) {
        SessionsResponse response = sessionService.findSessions(curriculumId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createSession(@PathVariable Long curriculumId,
                                              @RequestBody SessionRequest request) {
        Long sessionId = sessionService.createSession(curriculumId, request);

        return ResponseEntity.created(URI.create("/sessions/" + sessionId)).build();
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<Void> updateSession(@PathVariable Long curriculumId,
                                              @PathVariable Long sessionId,
                                              @RequestBody SessionRequest request) {
        sessionService.updateSession(sessionId, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long curriculumId,
                                              @PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);

        return ResponseEntity.noContent().build();
    }
}
