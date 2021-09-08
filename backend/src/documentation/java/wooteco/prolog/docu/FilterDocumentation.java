package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

import java.util.Arrays;
import java.util.List;

public class FilterDocumentation extends Documentation {

    @Test
    void 필터_목록을_조회한다() {
        LevelRequest levelRequest1 = new LevelRequest("레벨1");
        Long level1 = 레벨_등록함(levelRequest1);
        LevelRequest levelRequest2 = new LevelRequest("레벨2");
        Long level2 = 레벨_등록함(levelRequest2);

        MissionRequest request1 = new MissionRequest("지하철 노선도 미션 1", level1);
        미션_등록함(request1);
        MissionRequest request2 = new MissionRequest("지하철 노선도 미션 2", level2);
        미션_등록함(request2);

        List<TagRequest> tagRequests = Arrays.asList(
                new TagRequest("자바"),
                new TagRequest("파이썬"),
                new TagRequest("자바스크립트")
        );
        태그_등록함(tagRequests);

        given("filter/list")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/filters")
                .then().log().all().extract();
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

    private ExtractableResponse<Response> 미션_등록함(MissionRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract();
    }

    private ExtractableResponse<Response> 태그_등록함(List<TagRequest> request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/tags")
                .then()
                .log().all()
                .extract();
    }
}
