package wooteco.prolog.docu;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.roadmap.application.RecommendedPostService;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;
import wooteco.prolog.roadmap.ui.RecommendedController;

@WebMvcTest(controllers = RecommendedController.class)
public class RecommendDocument extends NewDocumentation {

    @MockBean
    RecommendedPostService recommendedPostService;

    @Test
    void 추천_포스트_생성() {
        RecommendedRequest recommendUrlValue = new RecommendedRequest("recommendUrlValue");

        given
            .contentType("application/json;charset=UTF-8")
            .body(recommendUrlValue)
            .when().post("/keywords/{keywordId}/recommended-posts", 1L)
            .then().log().all().apply(document("recommend/create"))
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 추천_포스트_수정() {
        RecommendedUpdateRequest recommendedUpdateRequest = new RecommendedUpdateRequest("recommendUrlValue");

        given
            .contentType("application/json;charset=UTF-8")
            .body(recommendedUpdateRequest)
            .when().put("/keywords/{keywordId}/recommended-posts/{recommendedId}", 1L, 2L)
            .then().log().all().apply(document("recommend/update"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 추천_포스트_삭제() {
        given
            .when().delete("/keywords/{keywordId}/recommended-posts/{recommendedId}", 1L, 2L)
            .then().log().all().apply(document("recommend/delete"))
            .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
