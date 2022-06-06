package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

class StudylogRssFeedDocumentation extends Documentation {

    @Test
    void RSS_피드를_조회한다() {
        // given
        스터디로그_등록함(Arrays.asList(studylogRequest1(), studylogRequest2()));

        // when
        ExtractableResponse<Response> response = given("rss-feed/read")
            .when().get("/rss")
            .then().log().all().extract();

        // then
        assertThat(response.contentType()).isEqualTo("application/rss+xml;charset=UTF-8");
    }

    private ExtractableResponse<Response> 스터디로그_등록함(List<StudylogRequest> request) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/studylogs")
            .then().log().all().extract();
    }

    private StudylogRequest studylogRequest1() {
        Long sessionId = 세션_등록함(new SessionRequest("세션 1"));
        Long missionId = 미션_등록함(new MissionRequest("세션 1 - 어떤 미션", sessionId));

        return new StudylogRequest(
            "어떤 타이틀",
            "어떤 내용",
            missionId,
            Arrays.asList(new TagRequest("어떤 태그 1"), new TagRequest("어떤 태그 2"))
        );
    }

    private StudylogRequest studylogRequest2() {
        Long sessionId = 세션_등록함(new SessionRequest("세션 2"));
        Long missionId = 미션_등록함(new MissionRequest("세션 2 - 어쩌구 미션", sessionId));

        return new StudylogRequest(
            "어쩌구 타이틀",
            "어쩌구 내용",
            missionId,
            Arrays.asList(new TagRequest("어쩌구 태그 1"), new TagRequest("어쩌구 태그 2"))
        );
    }

    private Long 세션_등록함(SessionRequest request) {
        return RestAssured
            .given().log().all()
            .body(request)
            .contentType(APPLICATION_JSON_VALUE)
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
            .contentType(APPLICATION_JSON_VALUE)
            .when()
            .post("/missions")
            .then()
            .log().all()
            .extract()
            .as(MissionResponse.class)
            .getId();
    }
}
