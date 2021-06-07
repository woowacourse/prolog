package wooteco.prolog.mission;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.AcceptanceTest;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MissionAcceptanceTest extends AcceptanceTest {

    @DisplayName("미션을 조회한다.")
    @Test
    void showCategories() {
        // given
        MissionRequest request1 = new MissionRequest("지하철 노선도 미션 1");
        ExtractableResponse<Response> mission1 = 미션_등록함(request1);
        MissionRequest request2 = new MissionRequest("지하철 노선도 미션 2");
        ExtractableResponse<Response> mission2 = 미션_등록함(request2);

        // when
        ExtractableResponse<Response> response = given()
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
    void 미션을_저장한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션");

        // when
        ExtractableResponse<Response> response = 미션_등록함(request);

        // then
        MissionResponse missionResponse = response.as(MissionResponse.class);
        assertThat(missionResponse.getId()).isEqualTo(1L);
        assertThat(missionResponse.getName()).isEqualTo(request.getName());
    }

    @Test
    void 중복된_이름으로_미션을_저장한다() {
        // given
        MissionRequest request = new MissionRequest("지하철 노선도 미션");
        미션_등록함(request);

        // when
        ExtractableResponse<Response> response = 미션_등록함(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat((String) response.jsonPath().get("message")).isEqualTo("미션이 중복됩니다.");
    }

    private ExtractableResponse<Response> 미션_등록함(MissionRequest request) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract();
    }
}
