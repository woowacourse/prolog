package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.session.application.dto.SessionRequest;

public class SessionStepDefinitions extends AcceptanceSteps {

    @Given("강의 여러개를 작성하고")
    public void 강의여러개를작성하고() {
        context.invokeHttpPost("/sessions", new SessionRequest("새로운강의1"));
        context.invokeHttpPost("/sessions", new SessionRequest("새로운강의2"));
        context.invokeHttpPost("/sessions", new SessionRequest("새로운강의3"));
    }

    @When("{string} 강의를 추가하(면)(고)")
    public void 강의를추가하면(String sessionName) {
        context.invokeHttpPost("/sessions", new SessionRequest(sessionName));
        context.storage.put("session", sessionName);
    }

    @Then("{string} 강의가 추가된다")
    public void 강의를추가된다(String sessionName) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("강의 목록을 조회하면")
    public void 강의목록을조회하면() {
        context.invokeHttpGet("/sessions");
    }

    @Then("강의 목록을 받는다")
    public void 강의목록을받는다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<String> sessions = context.response.jsonPath().getList("name");
        assertThat(sessions).isNotEmpty();
    }
}
