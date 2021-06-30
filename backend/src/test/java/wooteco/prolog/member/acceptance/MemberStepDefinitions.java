package wooteco.prolog.member.acceptance;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.acceptance.AcceptanceSteps;
import wooteco.prolog.login.GithubResponses;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberStepDefinitions extends AcceptanceSteps {
    @When("{string}의 멤버 정보를 조회하면")
    public void 멤버정보를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken("/members/" + username);
    }

    @Then("멤버 정보가 조회된다")
    public void 멤버정보가조회된다() {
        MemberResponse member = context.response.as(MemberResponse.class);

        assertThat(member.getImageUrl()).isNotNull();
    }

    @When("{string}(이)(이가) 자신의 유저네임을 {string}(로)(으로) 수정하면")
    public void 자신의유저네임을수정하면(String member, String updatedUsername) {
        String originUsername = GithubResponses.findByName(member).getLogin();

        MemberUpdateRequest updateRequest = new MemberUpdateRequest(
                updatedUsername, "", ""
        );

        context.invokeHttpPutWithToken("/members/" + originUsername, updateRequest);
    }

    @Then("유저네임이 {string}(로)(으로) 수정")
    public void 유저네임이으로수정(String username) {
        context.invokeHttpGet("/members/" + username);
        MemberResponse member = context.response.as(MemberResponse.class);

        assertThat(member.getUsername()).isEqualTo(username);
    }
}