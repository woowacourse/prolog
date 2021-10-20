package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Optional;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.application.dto.MemberScrapRequest;
import wooteco.prolog.member.application.dto.MemberUpdateRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

public class MemberStepDefinitions extends AcceptanceSteps {

    @When("{string}의 멤버 정보를 조회하면")
    public void 멤버정보를조회하면(String member) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGet("/members/" + username);
    }

    @When("자신의 멤버 정보를 조회하면")
    public void 자신의멤버정보를조회하면() {
        String username = (String) context.storage.get("username");
        context.invokeHttpGetWithToken("/members/" + username);
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
        String username = (String) context.storage.get("username");
        context.invokeHttpPutWithToken("/members/" + username, updateRequest);
    }

    @Then("{string}의 닉네임이 {string}(로)(으로) 수정")
    public void 닉네임이으로수정(String member, String nickname) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken("/members/" + username);
        String updatedNickname = context.response.as(MemberResponse.class).getNickname();

        assertThat(updatedNickname).isEqualTo(nickname);
    }

    @When("{string}의 닉네임이 {int}번 스터디로그를 스크랩하면")
    public void studylog를스크랩하면(String member, int studylogId){
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpPostWithToken("/members/" + username + "/scrap", new MemberScrapRequest((long)studylogId));
        assertThat(context.response.statusCode()).isEqualTo(200);
    }

    @When("{string}의 닉네임이 {int}번 스터디로그를 스크랩 취소하면")
    public void studylog를스크랩취소하면(String member, int studylogId){
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpDeleteWithToken("/members/" + username + "/scrap", new MemberScrapRequest((long)studylogId));
        assertThat(context.response.statusCode()).isEqualTo(200);
    }

    @Then("{string}의 닉네임이 스크랩한 {int}번 스터디로그를 볼 수 있다")
    public void 스크랩한studylog를볼수있다(String member, int studylogId){
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken("/members/" + username + "/scrap");
        assertThat(checkIfScrap(studylogId)).isTrue();
    }

    @Then("{string}의 닉네임이 스크랩한 {int}번 스터디로그를 볼 수 없다")
    public void 스크랩한studylog를볼수없다(String member, int studylogId) {
        String username = GithubResponses.findByName(member).getLogin();
        context.invokeHttpGetWithToken("/members/" + username + "/scrap");
        assertThat(checkIfScrap(studylogId)).isFalse();
    }

    private boolean checkIfScrap(int studylogId) {
        return context.response.as(StudylogsResponse.class).getData().stream()
            .anyMatch(studylog -> studylog.getId() == studylogId);
    }
}
