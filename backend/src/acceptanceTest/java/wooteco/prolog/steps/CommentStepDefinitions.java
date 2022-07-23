package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.CommentAcceptanceFixture;

public class CommentStepDefinitions extends AcceptanceSteps {

    @When("{long}번 스터디로그에 대한 댓글을 작성하면")
    public void 스터디로그에_대한_댓글을_작성하면(Long studylogId) {
        context.invokeHttpPostWithToken("/studylogs/" + studylogId + "/comments", CommentAcceptanceFixture.COMMENT1.getRequest());
    }

    @Then("댓글이 작성된다")
    public void 댓글이_작성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }
}
