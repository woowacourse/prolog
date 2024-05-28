package wooteco.prolog.article.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.article.application.MetaOgService;
import wooteco.prolog.article.application.dto.ArticleUrlRequest;
import wooteco.prolog.article.application.dto.ArticleUrlResponse;

@RequiredArgsConstructor
@RestController
public class MetaOgController {

    private final MetaOgService metaOgService;

    @GetMapping("/meta-og")
    public ResponseEntity<ArticleUrlResponse> parseUrl(
        @RequestParam("url") final ArticleUrlRequest articleUrlRequest) {
        final ArticleUrlResponse response = metaOgService.parse(articleUrlRequest);
        return ResponseEntity.ok(response);
    }
}
