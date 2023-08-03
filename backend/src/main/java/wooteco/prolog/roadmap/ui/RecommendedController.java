package wooteco.prolog.roadmap.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.roadmap.application.RecommendedService;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;

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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{recommendedId}")
    public ResponseEntity<Void> updateRecommendedPost(@PathVariable("keywordId") Long keywordId,
                                                      @PathVariable("recommendedId") Long recommendedId,
                                                      @RequestBody RecommendedUpdateRequest request) {
        recommendedService.update(recommendedId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recommendedId}")
    public ResponseEntity<Void> deleteRecommendedPost(@PathVariable("keywordId") Long keywordId,
                                                      @PathVariable("recommendedId") Long recommendedId) {
        recommendedService.delete(recommendedId);
        return ResponseEntity.noContent().build();
    }
}
