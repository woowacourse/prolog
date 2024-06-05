package wooteco.prolog.article.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wooteco.prolog.article.application.ArticleService;
import wooteco.prolog.article.application.dto.ArticleBookmarkRequest;
import wooteco.prolog.article.application.dto.ArticleLikesRequest;
import wooteco.prolog.article.application.dto.ArticleRequest;
import wooteco.prolog.article.application.dto.ArticleResponse;
import wooteco.prolog.article.domain.ArticleFilterType;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember;

import java.net.URI;
import java.util.List;

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

    @PutMapping("/{id}/bookmark")
    public ResponseEntity<Void> bookmarkArticle(@PathVariable final Long id,
                                                @AuthMemberPrincipal final LoginMember member,
                                                @RequestBody final ArticleBookmarkRequest request) {
        articleService.bookmarkArticle(id, member, request.getBookmark());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Void> likeArticle(@PathVariable final Long id,
                                            @AuthMemberPrincipal final LoginMember member,
                                            @RequestBody final ArticleLikesRequest request) {
        articleService.likeArticle(id, member, request.getLike());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getArticlesByPublishedAt(@AuthMemberPrincipal final LoginMember member,
                                                                @RequestParam("course") final ArticleFilterType course,
                                                                @RequestParam("onlyBookmarked") boolean onlyBookmarked) {
        final List<ArticleResponse> articleResponses = articleService.getArticlesByPublishedAt(member);

        return ResponseEntity.ok(articleResponses);
    }

    @PostMapping("/{id}/views")
    public ResponseEntity<Void> updateViewCount(@PathVariable final Long id) {
        articleService.updateViewCount(id);
        return ResponseEntity.ok().build();
    }

    // TODO: PutMapping으로 변경
    @GetMapping("/fetch")
    public ResponseEntity<List<ArticleResponse>> fetchArticles(@RequestParam(required = false) String username) {
        List<ArticleResponse> articleResponses = articleService.fetchArticlesOf(username);
        return ResponseEntity.ok().body(articleResponses);
    }
}
