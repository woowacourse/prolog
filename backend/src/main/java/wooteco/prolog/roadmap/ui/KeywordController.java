package wooteco.prolog.roadmap.ui;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.KeywordService;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;

@RestController
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @PostMapping("/sessions/{sessionId}/keywords")
    public ResponseEntity<Void> createKeyword(@PathVariable Long sessionId,
        @RequestBody KeywordCreateRequest createRequest) {
        Long keywordId = keywordService.createKeyword(sessionId, createRequest);
        return ResponseEntity.created(
            URI.create("/sessions/" + sessionId + "/keywords/" + keywordId)).build();
    }

    @GetMapping("/keywords/{keywordId}")
    public ResponseEntity<KeywordResponse> findKeyword(
        @PathVariable Long keywordId
    ) {
        KeywordResponse response = keywordService.findKeyword(keywordId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/keywords/{keywordId}")
    public ResponseEntity<Void> updateKeyword(
        @PathVariable Long keywordId,
        @RequestBody KeywordUpdateRequest updateRequest
    ) {
        keywordService.updateKeyword(keywordId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/keywords/{keywordId}")
    public ResponseEntity<Void> deleteKeyword(
        @PathVariable Long keywordId) {
        keywordService.deleteKeyword(keywordId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/keywords")
    public ResponseEntity<KeywordsResponse> findSessionIncludeRootKeywords() {
        KeywordsResponse response = keywordService.findRootKeywords();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/keywords/{keywordId}/children")
    public ResponseEntity<KeywordResponse> findChildrenKeywords(@PathVariable Long keywordId) {
        KeywordResponse response = keywordService.findKeywordWithAllChild(keywordId);
        return ResponseEntity.ok(response);
    }
}
