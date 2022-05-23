package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;

public class FilterStepDefinitions extends AcceptanceSteps {

    @When("필터요청이 들어오면")
    public void 필터요청이들어오면() {
        context.invokeHttpGetWithToken("/filters");
    }

    @Then("nickname을 기준으로 멤버데이터들을 오름차순 정렬하여 반환한다")
    public void nickname을기준으로멤버데이터들을오름차순정렬하여반환한다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(context.response.body().jsonPath().getList("members"))
                .extracting("nickname")
                .containsExactly("브라운", "서니", "현구막");
    }
}
