package wooteco.prolog.login;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

public class LoginDocumentation extends Documentation {

    @Test
    void create() {
        String code = "1234567890qazwsxedcrfvtgbyhnujmiklop";
        TokenRequest params = new TokenRequest(code);

        given("login/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class);
    }

    @Test
    void findMember() {
        given("members/me")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get("/members/me")
                .then().log().all()
                .extract();
    }
}
