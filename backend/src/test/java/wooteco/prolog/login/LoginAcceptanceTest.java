package wooteco.prolog.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.AcceptanceTest;
import wooteco.prolog.login.application.GithubClient;
import wooteco.prolog.login.application.dto.GithubProfileResponse;
import wooteco.prolog.login.application.dto.MemberResponse;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static wooteco.prolog.Documentation.MEMBER1;

@ExtendWith(MockitoExtension.class)
public class LoginAcceptanceTest extends AcceptanceTest {
    @MockBean
    GithubClient githubClient;

    @DisplayName("토큰에서 멤버 찾기 기능")
    @Test
    void tokenToMemberTest() {
        when(githubClient.getGithubProfileFromGithub(any()))
                .thenReturn(new GithubProfileResponse(MEMBER1.getNickname(), String.valueOf(MEMBER1.getGithubId()), MEMBER1.getImageUrl()));

        TokenResponse tokenResponse = given().log().all()
                .body(new TokenRequest("나는 코다 나는 눈이다"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().body().as(TokenResponse.class);

        MemberResponse memberResponse = given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/members/me")
                .then().log().all()
                .extract().as(MemberResponse.class);

        assertThat(memberResponse.getRole()).isEqualTo(MEMBER1.getRole());
        assertThat(memberResponse.getNickname()).isEqualTo(MEMBER1.getNickname());
        assertThat(memberResponse.getImageUrl()).isEqualTo(MEMBER1.getImageUrl());
    }
}
