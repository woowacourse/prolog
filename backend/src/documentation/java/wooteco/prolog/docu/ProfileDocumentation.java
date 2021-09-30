package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.report.application.dto.LevelRequest;
import wooteco.prolog.report.application.dto.LevelResponse;
import wooteco.prolog.report.application.dto.MissionRequest;
import wooteco.prolog.report.application.dto.MissionResponse;
import wooteco.prolog.report.application.dto.StudylogRequest;
import wooteco.prolog.report.application.dto.TagRequest;

public class ProfileDocumentation extends Documentation {

    @Test
    public void 스터디로그_목록을_작성자별로_조회한다() {
        스터디로그_등록함(Arrays.asList(createStudylogRequest1()));
        스터디로그_등록함(Arrays.asList(createStudylogRequest2()));

        given("profile/studylog")
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

    private StudylogRequest createStudylogRequest1() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long levelId = 레벨_등록함(new LevelRequest("레벨1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private StudylogRequest createStudylogRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long levelId = 레벨_등록함(new LevelRequest("레벨3"));
        Long missionId = 미션_등록함(new MissionRequest("레벨3 - 프로젝트", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("java"), new TagRequest("jpa"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private ExtractableResponse<Response> 스터디로그_등록함(List<StudylogRequest> request) {
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
