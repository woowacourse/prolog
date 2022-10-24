package wooteco.prolog.roadmap.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.KeywordService;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;

@RestController
@RequestMapping("/sessions")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping("/{sessionId}/keywords")
    public ResponseEntity<Void> createKeyword(@PathVariable Long sessionId,
                                              @RequestBody KeywordCreateRequest createRequest) {

        Long keywordId = keywordService.createKeyword(sessionId, createRequest);
        return ResponseEntity.created(URI.create("/sessions/" + sessionId + "/keywords/" + keywordId)).build();
    }
}
