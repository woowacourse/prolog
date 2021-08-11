package wooteco.prolog.docu;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

public class LoginDocumentation extends Documentation {

    @Test
    void 토큰을_생성한다() {
        TokenRequest params = new TokenRequest(GithubResponses.소롱.getCode());

        given("login/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class);
    }

    @Test
    void 자신의_사용자_정보를_조회한다() {
        given("members/me")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get("/members/me")
                .then().log().all()
                .extract();
    }

    @Test
    void 사용자_정보를_조회한다() {
        given("members/read")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get("/members/{username}", GithubResponses.소롱.getLogin())
                .then().log().all()
                .extract();
    }
}
