package wooteco.studylog.log.documentation;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.studylog.Documentation;

import java.util.HashMap;
import java.util.Map;

public class StudyLogDocumentation extends Documentation {
    @Test
    void create() {
        // when
        Map<String, String> params = new HashMap<>();
        params.put("title", "제목");
        params.put("body", "본문");
        params.put("member", "사용자");

        ExtractableResponse<Response> response =
                given("study-log")
                        .body(params)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .when().post("/study-logs")
                        .then().log().all().extract();

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

    }
}
