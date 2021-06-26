package wooteco.prolog.login.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.ko.먼저;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import wooteco.prolog.acceptance.AcceptanceContext;
import wooteco.prolog.login.GithubResponses;
import wooteco.prolog.login.application.dto.TokenResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@Profile("test")
public class LoginAcceptanceSteps {
    @Autowired
    public AcceptanceContext context;

    @Given("로그인을 하고")
    public void 로그인을하고() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.브라운.getCode());

        context.invokeHttpPost("/login/token", data);
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);
        context.accessToken = tokenResponse.getAccessToken();
    }

    @When("로그인을 하면")
    public void 로그인을하면() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.브라운.getCode());

        context.invokeHttpPost("/login/token", data);
    }

    @Then("액세스 토큰을 받는다")
    public void 액세스토큰을받는다() {
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);

        assertThat(tokenResponse).isNotNull();
    }
}
