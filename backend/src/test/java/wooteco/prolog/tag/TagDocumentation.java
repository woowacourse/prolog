package wooteco.prolog.tag;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TagDocumentation extends Documentation {

    @Test
    void 태그_목록을_조회한다() {
        // given
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션"));
        List<TagRequest> tags = Arrays.asList(new TagRequest("자바"), new TagRequest("파이썬"));

        PostRequest postRequest = new PostRequest(title, content, missionId, tags);
        List<PostRequest> params = Arrays.asList(postRequest);

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

    @Test
    void 태그를_생성한다() {
        // given
        List<TagRequest> tagRequests = Arrays.asList(
                new TagRequest("자바"),
                new TagRequest("파이썬")
        );

        // when
        ExtractableResponse<Response> response = given("tag/create")
                .body(tagRequests)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/tags")
                .then()
                .log().all()
                .extract();

        // then
        List<TagResponse> tagResponses = response.jsonPath().getList(".", TagResponse.class);
        List<String> tagNames = tagResponses.stream()
                .map(TagResponse::getName)
                .collect(Collectors.toList());
        List<String> expectedNames = tagRequests.stream()
                .map(TagRequest::getName)
                .collect(Collectors.toList());
        assertThat(tagNames).usingRecursiveComparison().isEqualTo(expectedNames);
    }


    @Test
    void 태그_요청에_중복되는_이름이_있는_경우_예외처리한다() {
        // given
        List<TagRequest> tagRequests = Arrays.asList(
                new TagRequest("자바"),
                new TagRequest("파이썬"),
                new TagRequest("자바")
        );

        // when
        ExtractableResponse<Response> response = given("tag/create/fail")
                .body(tagRequests)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/tags")
                .then()
                .log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat((String) response.jsonPath().get("message")).isEqualTo("태그가 중복됩니다.");
    }
}
