package wooteco.prolog.mission;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class MissionDocumentation extends Documentation {
    @Test
    void post() {
        RestAssured
                .given(spec).log().all()
                .filter(document("mission/list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/missions")
                .then().log().all().extract();
    }

    @DisplayName("미션을 조회한다.")
    @Test
    void showCategories() {
        // given
        MissionRequest request1 = new MissionRequest("로또 미션");
        ExtractableResponse<Response> mission1 = 미션_등록함(request1);
        MissionRequest request2 = new MissionRequest("블랙잭 미션");
        ExtractableResponse<Response> mission2 = 미션_등록함(request2);

        // when
        ExtractableResponse<Response> response = given("mission/list")
                .when()
                .get("/missions")
                .then()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
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
    void 미션를_저장한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션");

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
    void 중복된_이름으로_미션를_저장한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션");
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
}
