package wooteco.prolog.docu;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.article.application.ArticleService;
import wooteco.prolog.article.ui.ArticleBookmarkRequest;
import wooteco.prolog.article.ui.ArticleController;
import wooteco.prolog.article.ui.ArticleLikesRequest;

@WebMvcTest(controllers = ArticleController.class)
public class ArticleDocumentation extends NewDocumentation {

    @MockBean
    private ArticleService articleService;

    @Test
    void 아티클에_북마크를_변경한다() {
        //given, when
        final ArticleBookmarkRequest bookmarkRequest = new ArticleBookmarkRequest(true);

        final ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(APPLICATION_JSON)
            .body(bookmarkRequest)
            .when().put("/articles/{article-id}/bookmark", 1L)
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("article/bookmark"));
    }

    @Test
    void 아티클에_좋아요를_변경한다() {
        //given, when
        final ArticleLikesRequest articleLikesRequest = new ArticleLikesRequest(true);

        final ValidatableMockMvcResponse response = given
            .header("Authorization", "Bearer " + accessToken)
            .contentType(APPLICATION_JSON)
            .body(articleLikesRequest)
            .when().put("/articles/{article-id}/likes", 1L)
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("article/likes"));
    }
}
