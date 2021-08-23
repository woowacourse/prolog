package wooteco.prolog.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.post.application.dto.PostsResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileStepDefinitions extends AcceptanceSteps {

    @When("{string}의 멤버 프로필을 조회하면")
    public void 멤버프로필을조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/profile");
    }

    @Then("멤버 프로필이 조회된다")
    public void 멤버프로필이조회된다() {
        MemberResponse member = context.response.as(MemberResponse.class);

        assertThat(member.getImageUrl()).isNotNull();
    }

    @When("{string}의 프로필 포스트를 조회하면")
    public void 멤버프로필포스트를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/posts");
    }

    @Then("프로필 포스트가 조회된다")
    public void 프로필포스트가조회된다() {
        PostsResponse posts = context.response.as(PostsResponse.class);

        assertThat(posts.getData()).isNotEmpty();
    }
}