package wooteco.prolog.post.documentation;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.login.GithubResponses;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;
import java.util.List;

public class PostDocumentation extends Documentation {
//    @DisplayName("Post 관련 기능 테스트")
//    @Test
//    void post() {
//        List<PostRequest> params = Arrays.asList(createPostRequest());
//
//        ExtractableResponse<Response> createResponse = 포스트를_생성한다(params);
//
//        포스트_목록을_조회한다();
//
//        포스트_목록을_필터링한다();
//
//        String location = createResponse.header("Location");
//
//        포스트_단건을_조회한다(location);
//
//        포스트_목록을_작성자별로_조회한다();
//
//        포스트를_수정한다(location, editPostRequest());
//
//        포스트를_삭제한다(location);
//    }

    @Test
    public void 포스트를_생성한다() {
        // given
        PostRequest post = createPostRequest1();
        List<PostRequest> postRequest = Arrays.asList(post);

        // when
        ExtractableResponse<Response> createResponse = given("post/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(postRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/posts")
            .then().log().all().extract();

        // given
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isNotNull();
    }

    public void 포스트_단건을_조회한다(String location) {
        // given
        List<PostRequest> postRequests = Arrays.asList(createPostRequest1(), createPostRequest2());
        포스트_등록함(postRequests);

        given("post/read")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get(location)
            .then().log().all().extract();
    }

    @Test
    public void 포스트_목록을_조회한다() {
        // given
        List<PostRequest> postRequests = Arrays.asList(createPostRequest1(), createPostRequest2());
        포스트_등록함(postRequests);

        // when
        ExtractableResponse<Response> response = given("post/list")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/posts")
            .then().log().all().extract();

        // then
        List<PostResponse> postResponses = response.jsonPath().getList(".", PostResponse.class);
        List<String> postTitles = postResponses.stream()
            .map(PostResponse::getTitle)
            .collect(Collectors.toList());
        List<String> expectedTitles = postRequests.stream()
            .map(PostRequest::getTitle)
            .collect(Collectors.toList());
        assertThat(postTitles).usingRecursiveComparison().isEqualTo(expectedTitles);
    }

    @Test
    public void 포스트_목록을_필터링한다() {
        // given
        List<PostRequest> postRequests = Arrays.asList(createPostRequest1(), createPostRequest2());
        포스트_등록함(postRequests);

        // when
        ExtractableResponse<Response> response = given("post/filter")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/posts?missions=1&tags=1&tags=2")
            .then().log().all().extract();

        // given
        List<PostResponse> postResponses = response.jsonPath().get(".");
        assertThat(postResponses).hasSize(1);
    }

    @Test
    public void 포스트를_수정한다() {
        // given
        ExtractableResponse<Response> postResponse = 포스트_등록함(Arrays.asList(createPostRequest1()));
        String location = postResponse.header("Location");

        String title = "수정된 제목";
        String content = "수정된 내용";
        Long missionId = 미션_등록함(new MissionRequest("수정된 미션"));
        List<TagRequest> tags = Arrays.asList(
            new TagRequest("spa"),
            new TagRequest("edit")
        );
        PostRequest editPostRequest = new PostRequest(title, content, missionId, tags);

        // when
        ExtractableResponse<Response> editResponse = given("post/edit")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(editPostRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(location)
                .then().log().all()
                .extract();

        // then
        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public void 포스트_목록을_작성자별로_조회한다() {
        given("post/mine")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}/posts", GithubResponses.소롱.getLogin());
    }

    public void 포스트를_삭제한다(String location) {
        given("post/delete")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location);
    }

    private PostRequest createPostRequest1() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션"));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new PostRequest(title, content, missionId, tags);
    }

    private PostRequest createPostRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long missionId = 미션_등록함(new MissionRequest("레벨3 - 프로젝트"));
        List<TagRequest> tags = Arrays.asList(new TagRequest("java"), new TagRequest("jpa"));

        return new PostRequest(title, content, missionId, tags);
    }

    private ExtractableResponse<Response> 포스트_등록함(List<PostRequest> request) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/posts")
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
