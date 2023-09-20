package wooteco.prolog.admin.roadmap.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.admin.roadmap.application.AdminRecommendedPostService;
import wooteco.prolog.admin.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.admin.roadmap.application.dto.RecommendedUpdateRequest;

@RestController
@RequestMapping("/admin/keywords/{keywordId}/recommended-posts")
public class AdminRecommendedController {

    private final AdminRecommendedPostService adminRecommendedPostService;

    public AdminRecommendedController(final AdminRecommendedPostService adminRecommendedPostService) {
        this.adminRecommendedPostService = adminRecommendedPostService;
    }

    @PostMapping
    public ResponseEntity<Void> createRecommendedPost(@PathVariable("keywordId") final Long keywordId,
                                                      @RequestBody final RecommendedRequest request) {
        adminRecommendedPostService.create(keywordId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{recommendedId}")
    public ResponseEntity<Void> updateRecommendedPost(@PathVariable("keywordId") final Long keywordId,
                                                      @PathVariable("recommendedId") final Long recommendedId,
                                                      @RequestBody final RecommendedUpdateRequest request) {
        adminRecommendedPostService.update(recommendedId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recommendedId}")
    public ResponseEntity<Void> deleteRecommendedPost(@PathVariable("keywordId") final Long keywordId,
                                                      @PathVariable("recommendedId") final Long recommendedId) {
        adminRecommendedPostService.delete(recommendedId);
        return ResponseEntity.noContent().build();
    }
}
