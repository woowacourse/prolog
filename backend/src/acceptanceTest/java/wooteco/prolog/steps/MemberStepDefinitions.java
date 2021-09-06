package wooteco.prolog.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberStepDefinitions extends AcceptanceSteps {

    @When("{string}의 멤버 정보를 조회하면")
    public void 멤버정보를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username);
    }

    @When("자신의 멤버 정보를 조회하면")
    public void 자신의멤버정보를조회하면() {
        context.invokeHttpGetWithToken("/members/me");
    }

    @Then("멤버 정보가 조회된다")
    public void 멤버정보가조회된다() {
        MemberResponse member = context.response.as(MemberResponse.class);

        assertThat(member.getImageUrl()).isNotNull();
    }

    @When("자신의 닉네임을 {string}(로)(으로) 수정하면")
    public void 자신의닉네임을수정하면(String updatedNickname) {
        MemberUpdateRequest updateRequest = new MemberUpdateRequest(
                updatedNickname, ""
        );

        context.invokeHttpPutWithToken("/members/me", updateRequest);
    }

    @Then("{string}의 닉네임이 {string}(로)(으로) 수정")
    public void 닉네임이으로수정(String member, String nickname) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken("/members/" + username);
        String updatedNickname = context.response.as(MemberResponse.class).getNickname();

        assertThat(updatedNickname).isEqualTo(nickname);
    }
}
