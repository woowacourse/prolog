package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_RSS_XML_VALUE;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.LevelRequest;
import wooteco.prolog.session.application.dto.LevelResponse;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

class RssFeedDocumentation extends Documentation {

    @Test
    void RSS_피드를_조회한다() {
        // given
        스터디로그_등록함(Arrays.asList(studylogRequest1(), studylogRequest2()));

        // when
        ExtractableResponse<Response> response = given("rss-feed/read")
            .when().get("/rss-feeds")
            .then().log().all().extract();

        // then
        assertThat(response.contentType()).isEqualTo(APPLICATION_RSS_XML_VALUE);
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
        Long levelId = 레벨_등록함(new LevelRequest("레벨 1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨 1 - 어떤 미션", levelId));

        return new StudylogRequest(
            "어떤 타이틀",
            "어떤 내용",
            missionId,
            Arrays.asList(new TagRequest("어떤 태그 1"), new TagRequest("어떤 태그 2"))
        );
    }

    private StudylogRequest studylogRequest2() {
        Long levelId = 레벨_등록함(new LevelRequest("레벨 2"));
        Long missionId = 미션_등록함(new MissionRequest("레벨 2 - 어쩌구 미션", levelId));

        return new StudylogRequest(
            "어쩌구 타이틀",
            "어쩌구 내용",
            missionId,
            Arrays.asList(new TagRequest("어쩌구 태그 1"), new TagRequest("어쩌구 태그 2"))
        );
    }

    private Long 레벨_등록함(LevelRequest request) {
        return RestAssured
            .given().log().all()
            .body(request)
            .contentType(APPLICATION_JSON_VALUE)
            .when()
            .post("/levels")
            .then()
            .log().all()
            .extract()
            .as(LevelResponse.class)
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
