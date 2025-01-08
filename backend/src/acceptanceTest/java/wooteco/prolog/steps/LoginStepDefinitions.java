package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.member.application.dto.MemberResponse;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions extends AcceptanceSteps {

    @Given("{string}(이)(가) 크루역할로 로그인을 하고")
    public void 멤버가크루역할로로그인을하고(String member) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("code", GithubResponses.findByName(member).getCode());

        context.invokeHttpPost("/login/token", data);
        TokenResponse tokenResponse = context.response.as(TokenResponse.class);
        context.accessToken = tokenResponse.getAccessToken();

        context.storage.put("username", GithubResponses.findByName(member).getLogin());
        updateRoleToCrew();
    }

    private void updateRoleToCrew() {
        final String loginUsername = (String) context.storage.get("username");
        final Long memberId = findMemberIdByUsername(loginUsername);

        HashMap<String, Object> data = new HashMap<>();
        data.put("role", "CREW");

        context.invokeHttpPatchWithToken("/members/" + memberId + "/role", data);
    }

    private Long findMemberIdByUsername(final String username) {
        context.invokeHttpGet("/members/" + username);
        final MemberResponse memberResponse = context.response.as(MemberResponse.class);
        return memberResponse.getId();
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
