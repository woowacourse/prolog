package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.session.application.dto.SessionRequest;

public class SessionMemberStepDefinitions extends AcceptanceSteps {

    @Given("{string} 이 로그인을 하공")
    public void 멤버가로그인을하고(String member) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.findByName(member).getCode());

        context.invokeHttpPost("/login/token", data);
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);
        context.accessToken = tokenResponse.getAccessToken();

        context.storage.put("username", GithubResponses.findByName(member).getLogin());
    }

    @Given("{string} 추가하면")
    public void 강의를추가하면(String sessionName) {
        context.invokeHttpPost("/sessions", new SessionRequest(sessionName));
        context.storage.put("session", sessionName);
    }

    @When("{long} 강의에 자신을 등록하면")
    public void 강의에자신을등록(Long sessionId) {
        context.invokeHttpPostWithToken("sessions/" + sessionId + "/members/me");
    }

    @Then("강의에 내가 추가된다.")
    public void 강의에내가추가된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
