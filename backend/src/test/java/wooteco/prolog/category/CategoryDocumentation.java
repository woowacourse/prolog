package wooteco.prolog.category;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.category.application.dto.CategoryRequest;
import wooteco.prolog.category.application.dto.CategoryResponse;
import wooteco.prolog.login.GithubLoginService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class CategoryDocumentation extends Documentation {
    @MockBean
    private GithubLoginService githubLoginService;

    @Test
    void post() {
        when(githubLoginService.createToken(anyString())).thenReturn("asdf.asdf.asdf");

        RestAssured
                .given(spec).log().all()
                .filter(document("category/list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/categories")
                .then().log().all().extract();
    }

    @DisplayName("카테고리를 조회한다.")
    @Test
    void showCategories() {
        // given
        CategoryRequest request1 = new CategoryRequest("로또 미션");
        ExtractableResponse<Response> category1 = 카테고리_등록함(request1);
        CategoryRequest request2 = new CategoryRequest("블랙잭 미션");
        ExtractableResponse<Response> category2 = 카테고리_등록함(request2);

        // when
        ExtractableResponse<Response> response = given("category/list")
                .when()
                .get("/categories")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .log().all()
                .extract();

        // then
        List<CategoryResponse> categories = response.jsonPath().getList(".", CategoryResponse.class);
        assertThat(categories).usingRecursiveComparison().isEqualTo(
                Arrays.asList(
                        category1.as(CategoryResponse.class),
                        category2.as(CategoryResponse.class)));
    }

    @Test
    void 카테고리를_저장한다() {
        // given
        CategoryRequest request = new CategoryRequest("지하철 노선도 미션");

        // when
        ExtractableResponse<Response> response = given("category/create/success")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .extract();

        // then
        CategoryResponse categoryResponse = response.as(CategoryResponse.class);
        assertThat(categoryResponse.getName()).isEqualTo(request.getName());
    }

    @Test
    void 중복된_이름으로_카테고리를_저장한다() {
        // given
        CategoryRequest request = new CategoryRequest("지하철 노선도 미션");
        카테고리_등록함(request);

        // when
        ExtractableResponse<Response> response = given("category/create/fail")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .extract();
        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat((String) response.jsonPath().get("message")).isEqualTo("카테고리가 중복됩니다.");
    }

    private ExtractableResponse<Response> 카테고리_등록함(CategoryRequest request) {
        return RestAssured.given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .extract();
    }
}
