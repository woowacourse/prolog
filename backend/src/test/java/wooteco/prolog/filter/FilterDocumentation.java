package wooteco.prolog.filter;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class FilterDocumentation extends Documentation {
    @DisplayName("필터 목록 조회 기능")
    @Test
    void filterTest() {
        RestAssured
                .given(spec).log().all()
                .filter(document("filter/list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/filters")
                .then().log().all().extract();
    }

}
