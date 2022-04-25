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
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class ProfileDocumentation extends Documentation {

    @Test
    public void 스터디로그_목록을_작성자별로_조회한다() {
        스터디로그_등록함(Arrays.asList(createStudylogRequest1()));
        스터디로그_등록함(Arrays.asList(createStudylogRequest2()));

        given("profile/studylog")
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/members/{username}/studylogs", GithubResponses.소롱.getLogin());
    }

    @Test
    void 사용자_프로필을_조회한다() {
        given("profile/profile")
            .when().get("/members/{username}/profile", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    @Test
    void 사용자_소개글을_조회한다() {
        given("profile/profile-intro")
            .when().get("/members/{username}/profile-intro", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    @Test
    void 사용자가_본인의_소개글을_수정한다() {
        ProfileIntroRequest request = new ProfileIntroRequest("수정된 소개글 입니다.");
        given("profile/update-profile-intro")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().put("/members/{username}/profile-intro", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    private StudylogRequest createStudylogRequest1() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long sessionId = 세션_등록함(new SessionRequest("세션1"));
        Long missionId = 미션_등록함(new MissionRequest("세션1 - 지하철 노선도 미션", sessionId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private StudylogRequest createStudylogRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long sessionId = 세션_등록함(new SessionRequest("세션3"));
        Long missionId = 미션_등록함(new MissionRequest("세션3 - 프로젝트", sessionId));
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

    private Long 세션_등록함(SessionRequest request) {
        return RestAssured
            .given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/sessions")
            .then()
            .log().all()
            .extract()
            .as(SessionResponse.class)
            .getId();
    }
}
