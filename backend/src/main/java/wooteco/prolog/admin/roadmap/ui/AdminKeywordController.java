package wooteco.prolog.admin.roadmap.ui;

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
import wooteco.prolog.admin.roadmap.application.AdminKeywordService;
import wooteco.prolog.admin.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.admin.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.admin.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.admin.roadmap.application.dto.KeywordsResponse;

@RestController
@RequestMapping("/admin/sessions/{sessionId}/keywords")
public class AdminKeywordController {

    private final AdminKeywordService adminKeywordService;

    public AdminKeywordController(final AdminKeywordService adminKeywordService) {
        this.adminKeywordService = adminKeywordService;
    }

    @GetMapping
    public ResponseEntity<KeywordsResponse> findSessionIncludeRootKeywords(
        @PathVariable Long sessionId) {
        KeywordsResponse response = adminKeywordService.findSessionIncludeRootKeywords(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{keywordId}")
    public ResponseEntity<KeywordResponse> findKeyword(@PathVariable Long sessionId,
        @PathVariable Long keywordId) {
        KeywordResponse response = adminKeywordService.findKeyword(sessionId, keywordId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{keywordId}/children")
    public ResponseEntity<KeywordResponse> find(@PathVariable Long sessionId,
        @PathVariable Long keywordId) {
        KeywordResponse response = adminKeywordService.findKeywordWithAllChild(sessionId, keywordId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createKeyword(@PathVariable Long sessionId,
                                              @RequestBody KeywordCreateRequest createRequest) {
        Long keywordId = adminKeywordService.createKeyword(sessionId, createRequest);
        return ResponseEntity.created(
            URI.create("/sessions/" + sessionId + "/keywords/" + keywordId)).build();
    }

    @PutMapping("/{keywordId}")
    public ResponseEntity<Void> updateKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId,
                                              @RequestBody KeywordUpdateRequest updateRequest) {
        adminKeywordService.updateKeyword(sessionId, keywordId, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{keywordId}")
    public ResponseEntity<Void> deleteKeyword(@PathVariable Long sessionId,
                                              @PathVariable Long keywordId) {
        adminKeywordService.deleteKeyword(sessionId, keywordId);
        return ResponseEntity.noContent().build();
    }
}
