package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.session.application.dto.SessionRequest;

public class NewSessionStepDefinitions extends AcceptanceSteps {

    @Given("{int}번 커리큘럼에 {string} 세션을 작성하고")
    @When("{int}번 커리큘럼에 {string} 세션을 작성하면")
    public void 세션을_생성하고(int curriculumId, String name) {
        context.invokeHttpPost("/curriculums/" + curriculumId + "/sessions",
            new SessionRequest(name));
    }

    @Then("세션이 생성된다")
    public void 세션이_생성된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("{int}번 커리큘럼의 세션 목록을 조회하면")
    public void 세션_목록을_조회하면(int curriculumId) {
        context.invokeHttpGet("/curriculums/" + curriculumId + "/sessions");
    }

    @Then("세션 목록이 조회된다")
    public void 세션_목록이_조회된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @When("{int}번 커리큘럼의 {int}번 세션을 {string} 세션으로 수정하면")
    public void 세션을_수정하면(int curriculumId, int sessionId, String name) {
        context.invokeHttpPut("/curriculums/" + curriculumId + "/sessions/" + sessionId,
            new SessionRequest(name));
    }

    @Then("세션이 수정된다")
    public void 세션이_수정된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @When("{int}번 커리큘럼의 {int}번 세션을 삭제하면")
    public void 세션을_삭제하면(int curriculumId, int sessionId) {
        context.invokeHttpDelete("/curriculums/" + curriculumId + "/sessions/" + sessionId);
    }

    @Then("세션이 삭제된다")
    public void 세션이_삭제된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
