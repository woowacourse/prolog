package wooteco.prolog.article.ui;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wooteco.prolog.article.domain.ArticleService;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Void> createArticles(@RequestBody final ArticleRequest articleRequest,
                                               @AuthMemberPrincipal final LoginMember member) {
        final Long id = articleService.create(articleRequest, member);
        return ResponseEntity.created(URI.create("/articles/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticles() {
        final List<ArticleResponse> allArticles = articleService.getAll();
        return ResponseEntity.ok(allArticles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArticle(@RequestBody final ArticleRequest articleRequest,
                                              @AuthMemberPrincipal final LoginMember member,
                                              @PathVariable final Long id) {
        articleService.update(id, articleRequest, member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable final Long id,
                                              @AuthMemberPrincipal final LoginMember member) {
        articleService.delete(id, member);
        return ResponseEntity.noContent().build();
    }
}
