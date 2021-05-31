package wooteco.prolog.category;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.AcceptanceTest;
import wooteco.prolog.category.application.dto.CategoryRequest;
import wooteco.prolog.category.application.dto.CategoryResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @DisplayName("카테고리를 조회한다.")
    @Test
    void showCategories() {
        // given
        CategoryRequest request1 = new CategoryRequest("지하철 노선도 미션 1");
        ExtractableResponse<Response> category1 = 카테고리_등록함(request1);
        CategoryRequest request2 = new CategoryRequest("지하철 노선도 미션 2");
        ExtractableResponse<Response> category2 = 카테고리_등록함(request2);

        // when
        ExtractableResponse<Response> response = given()
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
        ExtractableResponse<Response> response = 카테고리_등록함(request);

        // then
        CategoryResponse categoryResponse = response.as(CategoryResponse.class);
        assertThat(categoryResponse.getId()).isEqualTo(1L);
        assertThat(categoryResponse.getName()).isEqualTo(request.getName());
    }

    @Test
    void 중복된_이름으로_카테고리를_저장한다() {
        // given
        CategoryRequest request = new CategoryRequest("지하철 노선도 미션");
        카테고리_등록함(request);

        // when
        ExtractableResponse<Response> response = 카테고리_등록함(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat((String) response.jsonPath().get("message")).isEqualTo("카테고리가 중복됩니다.");
    }

    private ExtractableResponse<Response> 카테고리_등록함(CategoryRequest request) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .extract();
    }
}
