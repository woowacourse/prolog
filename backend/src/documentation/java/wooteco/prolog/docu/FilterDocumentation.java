package wooteco.prolog.docu;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;
import java.util.List;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class FilterDocumentation extends Documentation {

    @Test
    void 필터_목록을_조회한다() {
        MissionRequest request1 = new MissionRequest("지하철 노선도 미션 1");
        미션_등록함(request1);
        MissionRequest request2 = new MissionRequest("지하철 노선도 미션 2");
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