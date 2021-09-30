package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.LevelAcceptanceFixture;
import wooteco.prolog.fixtures.MissionAcceptanceFixture;
import wooteco.prolog.report.application.dto.MissionRequest;
import wooteco.prolog.report.application.dto.MissionResponse;

public class MissionStepDefinitions extends AcceptanceSteps {

    @Given("레벨 여러개를 생성하고")
    public void 레벨여러개를생성하고() {
        context.invokeHttpPost("/levels", LevelAcceptanceFixture.level1);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        context.invokeHttpPost("/levels", LevelAcceptanceFixture.level2);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Given("미션 여러개를 생성하고")
    public void 미션여러개를생성하고() {
        context.invokeHttpPost("/missions", MissionAcceptanceFixture.mission1);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        context.invokeHttpPost("/missions", MissionAcceptanceFixture.mission2);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @When("{string} 미션 등록을 하(면/고)")
    public void 미션등록을하면(String mission) {
        context.invokeHttpPost("/missions", new MissionRequest(mission, 1L));
    }

    @Then("미션이 등록된다")
    public void 미션이등록된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Then("미션을 실패한다")
    public void 미션을실패한다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @When("미션 목록을 조회하면")
    public void 미션목록을조회하면() {
        context.invokeHttpGet("/missions");
    }

    @Then("미션 목록을 받는다")
    public void 미션목록을받는다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<MissionResponse> missions = context.response.jsonPath()
            .getList(".", MissionResponse.class);
        assertThat(missions).isNotEmpty();
    }
}
