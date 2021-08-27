package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.studylog.application.dto.*;

import java.util.Arrays;
import java.util.List;

public class ProfileDocumentation extends Documentation {

    @Test
    public void 포스트_목록을_작성자별로_조회한다() {
        포스트_등록함(Arrays.asList(createPostRequest1()));
        포스트_등록함(Arrays.asList(createPostRequest2()));

        given("profile/post")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/{username}/posts", GithubResponses.소롱.getLogin());
    }

    @Test
    void 사용자_프로필을_조회한다() {
        given("profile/profile")
                .when().get("/members/{username}/profile", GithubResponses.소롱.getLogin())
                .then().log().all()
                .extract();
    }

    private PostRequest createPostRequest1() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long levelId = 레벨_등록함(new LevelRequest("레벨1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new PostRequest(title, content, missionId, tags);
    }

    private PostRequest createPostRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long levelId = 레벨_등록함(new LevelRequest("레벨3"));
        Long missionId = 미션_등록함(new MissionRequest("레벨3 - 프로젝트", levelId));
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

    private Long 레벨_등록함(LevelRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/levels")
                .then()
                .log().all()
                .extract()
                .as(LevelResponse.class)
                .getId();
    }
}
