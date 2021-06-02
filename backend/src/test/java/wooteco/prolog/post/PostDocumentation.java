package wooteco.prolog.post;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;
import java.util.List;

public class PostDocumentation extends Documentation {
    @DisplayName("Post 관련 기능 테스트")
    @Test
    void post() {
        List<PostRequest> params = Arrays.asList(createPostRequest());

        ExtractableResponse<Response> createResponse = 포스트를_생성한다(params);

        포스트_목록을_조회한다();

        포스트_목록을_필터링한다();

        String location = createResponse.header("Location");

        포스트_단건을_조회한다(location);
    }

    private PostRequest createPostRequest() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션"));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new PostRequest(title, content, missionId, tags);
    }

    private void 포스트_목록을_필터링한다() {
        given("post/filter")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts?missions=1&missions=2&tags=1&tags=2")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> 포스트를_생성한다(List<PostRequest> params) {
        ExtractableResponse<Response> createResponse = given("post/create")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/posts")
                .then().log().all().extract();
        return createResponse;
    }

    private void 포스트_목록을_조회한다() {
        given("post/list")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts")
                .then().log().all().extract();
    }

    private void 포스트_단건을_조회한다(String location) {
        given("post/read")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get(location)
                .then().log().all().extract();
    }

    private Long 미션_등록함(MissionRequest request) {
        return RestAssured.given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract()
                .as(MissionResponse.class)
                .getId();
    }
}
