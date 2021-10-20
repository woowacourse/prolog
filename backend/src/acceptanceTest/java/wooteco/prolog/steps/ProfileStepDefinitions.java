package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

public class ProfileStepDefinitions extends AcceptanceSteps {

    @When("{string}의 멤버 프로필을 조회하면")
    public void 멤버프로필을조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username + "/profile");
    }

    @Then("{string}의 멤버 프로필이 조회된다")
    public void 멤버프로필이조회된다(String member) {
        String memberName = context.response.as(MemberResponse.class).getNickname();

        assertThat(memberName).isEqualTo(member);
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
