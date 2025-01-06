package wooteco.prolog.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.roadmap.application.dto.EssayAnswerRequest;
import wooteco.prolog.roadmap.application.dto.EssayAnswerUpdateRequest;

import static org.assertj.core.api.Assertions.assertThat;

public final class EssayAnswerStepDefinitions extends AcceptanceSteps {

    @Given("{long}번 퀴즈에 {string}(이)라는 답변을 생성하(면)(고)")
    public void 퀴즈에답변을생성하면(final long quidId, final String answer) {
        EssayAnswerRequest request = new EssayAnswerRequest(quidId, answer);
        context.invokeHttpPostWithToken("/essay-answers", request);
    }

    @When("{long}번 답변을 조회하면")
    public void 답변을조회하면(final long answerId) {
        context.invokeHttpGet("essay-answers/" + answerId);
    }

    @When("{long}번 답변을 {string}(으)로 수정하면")
    public void 답변을수정하면(final long answerId, final String answer) {
        EssayAnswerUpdateRequest request = new EssayAnswerUpdateRequest(answer);
        context.invokeHttpPatchWithToken("/essay-answers/" + answerId, request);
    }

    @When("{long}번 답변을 삭제하면")
    public void 답변을삭제하면(final long answerId) {
        context.invokeHttpDeleteWithToken("/essay-answers/" + answerId);
    }

    @When("{long}번 퀴즈에 대한 답변들을 조회하면")
    public void 퀴즈에대한답변을들조회하면(final long quizId) {
        context.invokeHttpGet("/quizzes/" + quizId + "/essay-answers");
    }

    @Then("답변이 생성된다")
    public void 답변이생성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("답변(들)이 조회된다")
    public void 답변이조회된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("답변이 수정된다")
    public void 답변이수정된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("답변이 삭제된다")
    public void 답변이삭제된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
