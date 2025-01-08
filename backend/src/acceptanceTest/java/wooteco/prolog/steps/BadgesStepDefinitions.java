package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.badge.domain.BadgeType;
import wooteco.prolog.common.exception.ExceptionDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG8;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG9;

public class BadgesStepDefinitions extends AcceptanceSteps {

    @When("존재하지 않는 멤버의 배지를 조회하면")
    public void 존재하지않는멤버의배지를조회하면() {
        String username = "notFound";
        context.invokeHttpGet("/members/" + username + "/badges");
    }

    @Then("존재하지 않는 멤버 관련 예외가 발생한다")
    public void 존재하지않는멤버관련예외가발생한다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(context.response.as(ExceptionDto.class).getCode()).isEqualTo(1004);
    }

    @Given("여러개의 스터디로그를 작성하고")
    public void 여러개의스터디로그를작성하고() {
        for (int i = 0; i < 5; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG8.getStudylogRequest());
        }
        for (int i = 0; i < 2; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG9.getStudylogRequest());
        }
    }

    @When("배지를 조회하면")
    public void 배지를조회하면() {
        String username = String.valueOf(context.storage.get("username"));
        context.invokeHttpGet("/members/" + username + "/badges");
    }

    @Then("열정왕 배지를 부여한다.")
    public void 열정왕배지를부여한다() {
        List<String> badgeNames = context.response.jsonPath().getList("badges.name", String.class);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(badgeNames).containsExactly(BadgeType.PASSION_KING.name());
    }
}
