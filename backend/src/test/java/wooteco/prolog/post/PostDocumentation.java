package wooteco.prolog.post;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.login.GithubLoginService;
import wooteco.prolog.post.application.dto.PostRequest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PostDocumentation extends Documentation {
    @MockBean
    private GithubLoginService githubLoginService;

    @Test
    void post() {
        when(githubLoginService.createToken(anyString())).thenReturn("asdf.asdf.asdf");

        List<PostRequest> params = Arrays.asList(createPostRequest());

        ExtractableResponse<Response> createResponse = given("post/create")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/posts")
                .then().log().all().extract();

        given("post/list")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts")
                .then().log().all().extract();

        String location = createResponse.header("Location");

        given("post/read")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get(location)
                .then().log().all().extract();
    }

    private PostRequest createPostRequest() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long categoryId = 카테고리_등록함(new CategoryRequest("레벨1 - 지하철 노선도 미션"));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new PostRequest(title, content, categoryId, tags);
    }

    private Long 카테고리_등록함(CategoryRequest request) {
        return RestAssured.given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/categories")
                .then()
                .log().all()
                .extract()
                .as(CategoryResponse.class)
                .getId();
    }
}
