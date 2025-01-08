package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.member.application.dto.ProfileIntroRequest;
import wooteco.prolog.member.application.dto.ProfileIntroResponse;
import wooteco.prolog.member.application.dto.ProfileResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

public class ProfileStepDefinitions extends AcceptanceSteps {

    @When("{string}의 멤버 프로필을 조회하면")
    public void 멤버프로필을조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/profile");
    }

    @When("{string}의 멤버 프로필 인트로를 조회하면")
    public void 멤버프로필인트로를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/profile-intro");
    }

    @When("{string}의 멤버 프로필 인트로를 수정하(면/고)")
    public void 멤버프로필인트로를수정하면(String member) {
        ProfileIntroRequest updateRequest = new ProfileIntroRequest("안녕하세요 :)");
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpPutWithToken("/members/" + username + "/profile-intro", updateRequest);
    }

    @Then("{string}의 멤버 프로필이 조회된다")
    public void 멤버프로필이조회된다(String member) {
        String memberName = context.response.as(ProfileResponse.class).getNickname();

        assertThat(memberName).isEqualTo(member);
    }

    @Then("{string}의 멤버 프로필 인트로가 조회된다")
    public void 멤버프로필인트로가조회된다(String member) {
        String text = context.response.as(ProfileIntroResponse.class).getText();

        assertThat(text).isNotNull();
    }

    @Then("{string}의 멤버 프로필 인트로가 수정된다")
    public void 멤버프로필인트로가수정된다(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/profile-intro");
        String text = context.response.as(ProfileIntroResponse.class).getText();

        assertThat(text).isNotNull();
    }

    @When("{string}의 프로필 스터디로그를 조회하면")
    public void 멤버프로필스터디로그를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/posts");
    }

    @Then("{string}의 프로필 스터디로그가 조회된다")
    public void 프로필스터디로그가조회된다(String member) {
        String author = context.response.as(StudylogsResponse.class)
            .getData()
            .get(0)
            .getAuthor()
            .getNickname();

        assertThat(author).isEqualTo(member);
    }
}
