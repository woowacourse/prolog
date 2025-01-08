package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.roadmap.application.dto.QuizRequest;

public class QuizStepDefinitions extends AcceptanceSteps {


    //:todo keyword 와 합친 이후에 작성 예정
    @When("퀴즈를 생성하면")
    public void 퀴즈를_생성하면(Long sessionId, Long keywordId) {
        final QuizRequest 수달이_만든_테스트_퀴즈 = new QuizRequest("수달이 만든 테스트 퀴즈");
        context.invokeHttpPost("", 수달이_만든_테스트_퀴즈);
    }

    @Then("퀴즈가 추가된다")
    public void 퀴즈가_추가된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }
}
