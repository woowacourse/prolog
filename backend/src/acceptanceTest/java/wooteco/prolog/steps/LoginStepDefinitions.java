package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.login.application.dto.TokenResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions extends AcceptanceSteps {
    @Given("{string}(이)(가) 로그인을 하고")
    public void 멤버가로그인을하고(String member) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.findByName(member).getCode());

        context.invokeHttpPost("/login/token", data);
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);
        context.accessToken = tokenResponse.getAccessToken();
    }

    @When("{string}(이)(가) 로그인을 하면")
    public void 로그인을하면(String member) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.findByName(member).getCode());

        context.invokeHttpPost("/login/token", data);
    }

    @Then("액세스 토큰을 받는다")
    public void 액세스토큰을받는다() {
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);

        assertThat(tokenResponse).isNotNull();
    }
}
