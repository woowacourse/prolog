package wooteco.prolog.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.studylog.application.dto.TagResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TagStepDefinitions extends AcceptanceSteps {

    @Then("태그도 작성된다")
    public void 태그도작성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("태그 목록을 조회하면")
    public void 태그목록을조회하면() {
        context.invokeHttpGet("/tags");
    }

    @Then("태그 목록을 받는다")
    public void 태그목록을받는다() {
        List<TagResponse> tags = context.response.jsonPath().getList(".", TagResponse.class);

        assertThat(tags.size()).isEqualTo(4);
    }
}
