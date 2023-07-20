package wooteco.prolog.roadmap.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.roadmap.application.RecommendedService;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;

import java.net.URI;

@RestController
@RequestMapping("/keywords/{keywordId}/recommended-posts")
public class RecommendedController {

    private final RecommendedService recommendedService;

    public RecommendedController(final RecommendedService recommendedService) {
        this.recommendedService = recommendedService;
    }

    @PostMapping
    public ResponseEntity<Void> createRecommendedPost(@PathVariable("keywordId") Long keywordId,
                                                      @RequestBody RecommendedRequest request) {
        final Long id = recommendedService.create(keywordId, request);
        return ResponseEntity.created(
            URI.create("/keywords/" + keywordId + "/recommended-posts/" + id)).build();
    }

    @PutMapping("/{recommendedId}")
    public ResponseEntity<Void> updateRecommendedPost(@PathVariable("keywordId") Long keywordId,
                                                      @PathVariable("recommendedId") Long recommendedId,
                                                      @RequestBody RecommendedUpdateRequest request) {
        recommendedService.update(recommendedId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recommendedId}")
    public ResponseEntity<Void> deleteRecommendedPost(@PathVariable("keywordId") Long keywordId,
                                                      @PathVariable("recommendedId") Long recommendedId) {
        recommendedService.delete(recommendedId);
        return ResponseEntity.noContent().build();
    }
}
