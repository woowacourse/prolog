package wooteco.prolog.tag;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.tag.dto.TagRequest;
import wooteco.prolog.tag.dto.TagResponse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TagDocumentation extends Documentation {
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
        assertThat((String) response.jsonPath().get("message")).isEqualTo("중복된 태그를 입력할 수 없습니다.");
    }
}
