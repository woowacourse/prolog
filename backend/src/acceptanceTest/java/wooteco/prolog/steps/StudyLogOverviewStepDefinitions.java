package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.member.ui.StudyLogOverviewController.MyTagResponses;

public class StudyLogOverviewStepDefinitions extends AcceptanceSteps {

    @When("나의 태그 목록을 조회하면")
    public void 나의태그목록을조회하면() {
        context.invokeHttpGetWithToken("/members/me/tags");
    }

    @Then("나의 태그 목록이 조회된다")
    public void 나의태그목록이조회된다() {
        final MyTagResponses myTagResponses = context.response.as(MyTagResponses.class);

        //TODO: api 완료 후 Fixture 를 이용해 더 디테일한 검증 테스트 필요
        assertThat(myTagResponses.getData()).isNotNull();
    }
}
