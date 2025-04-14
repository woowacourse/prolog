package wooteco.prolog.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.session.application.dto.QuestionRequest;

import static org.apache.http.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

public class QuestionStepDefinitions extends AcceptanceSteps {

    @And("{string} 질문을 생성하고")
    @When("{string} 질문을 생성하면")
    public void 질문을생성하면(final String question) {
        context.invokeHttpPost("/questions", new QuestionRequest(1L, question));
    }

    @Then("질문이 생성된다")
    public void 질문이생성된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("질문을 조회하면")
    public void 질문을조회하면() {
        final var questionId = context.response.getHeader(LOCATION).replaceAll("/questions/", "");
        context.invokeHttpGet("/questions/" + questionId);
    }

    @Then("질문이 조회된다")
    public void 질문이조회된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
