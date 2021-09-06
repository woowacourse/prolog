package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.studylog.application.dto.LevelRequest;
import wooteco.prolog.studylog.application.dto.LevelResponse;
import wooteco.prolog.studylog.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.MissionResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MissionDocumentation extends Documentation {
    private Long levelId;

    @BeforeEach
    void setUp() {
        levelId = 레벨_등록함(new LevelRequest("레벨1"));
    }

    @Test
    void 미션_목록을_조회한다() {
        // given
        MissionRequest request1 = new MissionRequest("로또 미션", levelId);
        ExtractableResponse<Response> mission1 = 미션_등록함(request1);
        MissionRequest request2 = new MissionRequest("블랙잭 미션", levelId);
        ExtractableResponse<Response> mission2 = 미션_등록함(request2);

        // when
        ExtractableResponse<Response> response = given("mission/list")
                .when()
                .get("/missions")
                .then()
                .log().all()
                .extract();

        // then
        List<MissionResponse> categories = response.jsonPath().getList(".", MissionResponse.class);
        assertThat(categories).usingRecursiveComparison().isEqualTo(
                Arrays.asList(
                        mission1.as(MissionResponse.class),
                        mission2.as(MissionResponse.class)));
    }

    @Test
    void 미션을_저장한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션", levelId);

        // when
        ExtractableResponse<Response> response = given("mission/create/success")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract();

        // then
        MissionResponse missionResponse = response.as(MissionResponse.class);
        assertThat(missionResponse.getName()).isEqualTo(request.getName());
    }

    @Test
    void 미션_이름이_중복될_경우_예외처리한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션", levelId);
        미션_등록함(request);

        // when
        ExtractableResponse<Response> response = given("mission/create/fail")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat((String) response.jsonPath().get("message")).isEqualTo("미션이 중복됩니다.");
    }

    private ExtractableResponse<Response> 미션_등록함(MissionRequest request) {
        return RestAssured.given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract();
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
