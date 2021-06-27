package wooteco.prolog.member.acceptance;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.acceptance.AcceptanceSteps;
import wooteco.prolog.login.application.dto.MemberResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberStepDefinitions extends AcceptanceSteps {
    @When("자신의 멤버 정보를 조회하면")
    public void 자신의멤버정보를조회하면() {
        context.invokeHttpGetWithToken("/members/me");
    }

    @Then("멤버 정보가 조회된다")
    public void 멤버정보가조회된다() {
        MemberResponse member = context.response.as(MemberResponse.class);

        assertThat(member.getImageUrl()).isNotNull();
    }
}