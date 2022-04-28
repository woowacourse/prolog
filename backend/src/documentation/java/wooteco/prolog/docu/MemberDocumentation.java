package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.member.application.dto.MemberScrapRequest;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class MemberDocumentation extends Documentation {

    @Test
    void 사용자_본인_정보를_조회한다() {
        given("members/me")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get("/members/me")
            .then().log().all()
            .extract();
    }

    @Test
    void 자신의_사용자_정보를_수정한다() {
        MemberUpdateRequest memberUpdateRequest = new MemberUpdateRequest(
            "다른이름",
            "https://avatars.githubusercontent.com/u/51393021?v=4"
        );
        given("members/edit")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(memberUpdateRequest)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/members/{username}", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    @Test
    void 사용자_정보를_조회한다() {
        given("members/read")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get("/members/{username}", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract();
    }

    @Test
    void 스터디로그를_스크랩한다() {
        //given
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest(1));
        String location = studylogResponse.header("Location");
        String logId = location.substring(location.lastIndexOf('/') + 1);

        //when
        String scrapedLogLocation = given("members/scrap/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberScrapRequest(Long.valueOf(logId)))
            .when()
            .post("/members/{nickname}/scrap", GithubResponses.소롱.getLogin())
            .header("Location");

        //then
        RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get(scrapedLogLocation)
            .then().log().all()
            .assertThat()
            .body("id", equalTo(Integer.parseInt(logId)));
    }

    @Test
    void 스터디로그를_스크랩취소한다() {
        //given
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest(1));
        String location = studylogResponse.header("Location");
        Long logId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        스크랩_등록함(logId);

        //when & then
        given("members/scrap/delete")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when()
            .delete("/members/{nickname}/scrap?studylog="+ logId, GithubResponses.소롱.getLogin())
            .then().log().all()
            .assertThat()
            .statusCode(equalTo(HttpStatus.NO_CONTENT.value()));
    }

    @Test
    void 스크랩한_스터디로그를_목록을_조회한다() {
        //given
        int scrapSize = 4;
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest(scrapSize));
        String location = studylogResponse.header("Location");
        Long logId = Long.valueOf(location.substring(location.lastIndexOf('/') + 1));

        for (int i = 0; i < scrapSize; i++) {
            스크랩_등록함(logId + i);
        }

        //when
        int listSize = given("members/scrap/list")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberScrapRequest(logId))
            .when()
            .get("/members/{nickname}/scrap", GithubResponses.소롱.getLogin())
            .then().log().all()
            .extract()
            .as(StudylogsResponse.class)
            .getData()
            .size();

        //then
        assertThat(listSize).isEqualTo(scrapSize);
    }

    private List<StudylogRequest> createStudylogRequest(int size) {
        List<StudylogRequest> studylogRequests = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String title = "뭐라도 스터디로그가 있어야하니까";
            String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
            Long sessionId = 세션_등록함(new SessionRequest("세션" + i));
            Long missionId = 미션_등록함(new MissionRequest(String.format("세션%d 미션", i), sessionId));
            List<TagRequest> tags = Arrays.asList(new TagRequest(String.format("%d번 태그", i)));

            studylogRequests.add(new StudylogRequest(title, content, missionId, tags));
        }
        return studylogRequests;
    }

    private String 스크랩_등록함(Long logId) {
        return RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(new MemberScrapRequest(logId))
            .when()
            .post("/members/{nickname}/scrap", GithubResponses.소롱.getLogin())
            .header("Location");
    }

    private ExtractableResponse<Response> 스터디로그_등록함(List<StudylogRequest> studylogRequests) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(studylogRequests)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/posts")
            .then().log().all().extract();
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
