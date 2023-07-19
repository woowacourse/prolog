package wooteco.prolog.roadmap.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.KeywordService;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;

@RestController
@RequestMapping("/sessions/{sessionId}/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping
    public ResponseEntity<Void> createKeyword(@PathVariable Long sessionId,
                                              @RequestBody KeywordCreateRequest createRequest) {
        Long keywordId = keywordService.createKeyword(sessionId, createRequest);
        return ResponseEntity.created(
            URI.create("/sessions/" + sessionId + "/keywords/" + keywordId)).build();
    }

    @GetMapping("/{keywordId}")
    public ResponseEntity<KeywordResponse> findKeyword(@PathVariable Long sessionId,
                                                       @PathVariable Long keywordId) {
        KeywordResponse response = keywordService.findKeyword(sessionId, keywordId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{keywordId}")
    public ResponseEntity<Void> updateKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId,
                                              @RequestBody KeywordUpdateRequest updateRequest) {
        keywordService.updateKeyword(sessionId, keywordId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{keywordId}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId) {
        keywordService.deleteKeyword(sessionId, keywordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<KeywordsResponse> findSessionIncludeRootKeywords(
        @PathVariable Long sessionId) {
        KeywordsResponse response = keywordService.findSessionIncludeRootKeywords(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{keywordId}/children")
    public ResponseEntity<KeywordResponse> find(@PathVariable Long sessionId,
                                                @PathVariable Long keywordId) {
        KeywordResponse response = keywordService.findKeywordWithAllChild(sessionId, keywordId);
        return ResponseEntity.ok(response);
    }
}
