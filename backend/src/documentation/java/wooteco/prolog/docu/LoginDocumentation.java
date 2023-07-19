package wooteco.prolog.docu;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.ui.GithubLoginController;

@WebMvcTest(controllers = GithubLoginController.class)
public class LoginDocumentation extends NewDocumentation {

    @MockBean
    private GithubLoginService loginService;

    @Test
    void 토큰을_생성한다() {
        //given
        given(loginService.createToken(any()))
            .willReturn(TokenResponse.of(accessToken));

        //when
        TokenRequest params = new TokenRequest(GithubResponses.소롱.getCode());
        ValidatableMockMvcResponse response = given
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(params)
            .when().post("/login/token")
            .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("login/token"));
    }
}
