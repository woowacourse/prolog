package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;

public class KeywordRecommendedPostStepDefinitions extends AcceptanceSteps {

    @Given("{int}번 키워드에 대해 추천 포스트 {string}를 작성하고")
    @When("{int}번 키워드에 대해 추천 포스트 {string}를 작성하면")
    public void 추천_포스트를_추가하면(int keywordId, String url) {
        context.invokeHttpPost(
            "/keywords/"+keywordId+"/recommended-posts",
            new RecommendedRequest(url)
        );
    }

    @When("{int}번 키워드에 대한 {int}번 추천 포스트를 {string}로 수정하면")
    public void 추천_포스트를_수정하면(int keywordId, int recommendedId, String url) {
        context.invokeHttpPut(
            "/keywords/"+keywordId+"/recommended-posts/"+recommendedId,
            new RecommendedRequest(url));
    }

    @When("{int}번 키워드에 대한 {int}번 추천 포스트를 삭제하면")
    public void 추천_포스트를_삭제하면(int keywordId, int recommendedId) {
        context.invokeHttpDelete(
            "/keywords/" + keywordId + "/recommended-posts/" + recommendedId
        );
    }

    @Then("추천 포스트가 생성된다")
    public void 추천_포스트가_생성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Then("추천 포스트가 수정된다")
    public void 추천_포스트가_수정된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("추천 포스트가 삭제된다")
    public void 추천_포스트가_삭제된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
