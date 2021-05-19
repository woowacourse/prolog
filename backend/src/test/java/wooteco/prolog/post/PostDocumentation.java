package wooteco.prolog.post;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.post.web.controller.dto.PostRequest;
import wooteco.prolog.post.web.controller.dto.PostResponse;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class PostDocumentation extends Documentation {
    @Test
    void post() {
        List<PostRequest> params = Arrays.asList(createPostRequest(), createPostRequest());

        ExtractableResponse<Response> createResponse = RestAssured
                .given(spec).log().all()
                .filter(document("post/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/posts")
                .then().log().all().extract();

        RestAssured
                .given(spec).log().all()
                .filter(document("post/list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts")
                .then().log().all().extract();

        String location = createResponse.header("Location");

        RestAssured
                .given(spec).log().all()
                .filter(document("post/read",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get(location)
                .then().log().all().extract();
    }

    private PostRequest createPostRequest() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        String category = "레벨1 - 지하철 노선도 미션";
        List<String> tags = Arrays.asList("spa", "router");
        return new PostRequest(title, content, category, tags);
    }
}
