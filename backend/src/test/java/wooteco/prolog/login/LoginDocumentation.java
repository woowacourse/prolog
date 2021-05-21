package wooteco.prolog.login;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class LoginDocumentation extends Documentation {
    @Test
    void create() {
        String code = "1234567890qazwsxedcrfvtgbyhnujmiklop";
        TokenRequest params = new TokenRequest(code);

        RestAssured
                .given(spec).log().all()
                .filter(document("login/token",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all().extract();
    }
}
