package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.report.application.dto.LevelRequest;
import wooteco.prolog.report.application.dto.LevelResponse;
import wooteco.prolog.report.application.dto.MissionRequest;
import wooteco.prolog.report.application.dto.MissionResponse;
import wooteco.prolog.report.application.dto.StudylogRequest;
import wooteco.prolog.report.application.dto.TagRequest;
import wooteco.prolog.report.application.dto.TagResponse;

public class TagDocumentation extends Documentation {

    @Test
    void 태그_목록을_조회한다() {
        // given
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long levelId = 레벨_등록함(new LevelRequest("레벨1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("자바"), new TagRequest("파이썬"));

        StudylogRequest studylogRequest = new StudylogRequest(title, content, missionId, tags);
        List<StudylogRequest> params = Arrays.asList(studylogRequest);

        RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(params)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/posts")
            .then()
            .log().all();

        // when
        ExtractableResponse<Response> response = given("tag/list")
            .when()
            .get("/tags")
            .then()
            .log().all()
            .extract();

        // then
        List<TagResponse> tagResponses = response.jsonPath().getList(".", TagResponse.class);
        List<String> tagNames = tagResponses.stream()
            .map(TagResponse::getName)
            .collect(Collectors.toList());
        List<String> expectedNames = tags.stream()
            .map(TagRequest::getName)
            .collect(Collectors.toList());
        assertThat(tagNames).usingRecursiveComparison().isEqualTo(expectedNames);
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
