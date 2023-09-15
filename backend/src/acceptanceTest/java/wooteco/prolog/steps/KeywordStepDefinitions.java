package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.KeywordAcceptanceFixture.KEYWORD_REQUEST;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.session.application.dto.SessionRequest;

public class KeywordStepDefinitions extends AcceptanceSteps {

    /**
     * MissionStepDefinitions 에 세션을 만드는 내용이 있지만 세션 1, 세션 2로 만들고 있어서 새롭게 생성하였음 추후 논의 후
     * MissionDefinitions 의 세션을 제거
     */
    @Given("{string} 세션을 생성하고 - {int}번 세션")
    public void 세션을_생성하고(String sessionName, int number) {
        context.invokeHttpPost("/sessions", new SessionRequest(sessionName));
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Given("{int}번 세션에 {string}라는 키워드를 순서 {int}, 중요도 {int}로 작성하고")
    @When("{int}번 세션에 {string}라는 키워드를 순서 {int}, 중요도 {int}로 작성하면")
    public void 키워드를_작성하고(int sessionId, String keywordName, int seq, int importance) {
        context.invokeHttpPost(
            "/sessions/" + sessionId + "/keywords",
            KEYWORD_REQUEST.getSaveParent(keywordName, seq, importance));
    }

    @Given("{int}번 세션에 {string}라는 키워드를 순서 {int}, 중요도 {int}, 부모 키워드 {long}로 작성하고")
    public void 키워드를_부모_키워드와_함께_작성하고(int sessionId, String keywordName, int seq, int importance,
                                     long parentId) {
        context.invokeHttpPost(
            "/sessions/" + sessionId + "/keywords",
            KEYWORD_REQUEST.getSaveChild(keywordName, seq, importance, parentId));
    }

    @When("{int}번 세션과 {int}번 키워드를 조회하면")
    public void 키워드를_조회하면(int sessionId, int keywordId) {
        context.invokeHttpGet(
            "/keywords/" + keywordId
        );
    }

    @When("{int}번 세션과 {int}번 키워드를 키워드 {string}, 순서 {int}, 중요도 {int}로 수정하면")
    public void 키워드를_수정하면(int sessionId, int keywordId, String keywordName, int seq,
                          int importance) {
        context.invokeHttpPut(
            "/keywords/" + keywordId,
            KEYWORD_REQUEST.getUpdateParent(keywordName, seq, importance));
    }

    @When("{int}번 세션에 대한 {int}번 키워드를 삭제하면")
    public void 키워드를_삭제하면(int sessionId, int keywordId) {
        context.invokeHttpDelete(
            "/sessions/" + sessionId + "/keywords/" + keywordId
        );
    }

    @When("{int}번 세션에 대해서 최상위 키워드들을 조회하면")
    public void 세션에_대해서_키워드들을_조회하면(int sessionId) {
        context.invokeHttpGet(
            "/sessions/" + sessionId + "/keywords"
        );
    }

    @When("{int}번 세션에 대한 {int}번 키워드와 자식 키워드를 함께 조회하면")
    public void 키워드와_자식_키워드를_함께_조회하면(int sessionId, int keywordId) {
        context.invokeHttpGet(
            "/sessions/" + sessionId + "/keywords/" + keywordId + "/children"
        );
    }

    @Then("키워드가 생성된다")
    public void 키워드가_생성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Then("키워드가 조회된다")
    public void 키워드가_조회된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("키워드가 수정된다")
    public void 키워드가_수정된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("키워드가 삭제된다")
    public void 키워드가_삭제된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("키워드 목록이 조회된다")
    public void 키워드_목록이_조회된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @When("{int}번 세션, {int}번 키워드에 퀴즈를 작성하고")
    @When("{int}번 세션, {int}번 키워드에 퀴즈를 작성하면")
    public void 키워드에_퀴즈를_작성하면(int sessionId, int keywordId) {

        context.invokeHttpPost(
            "/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs",
            KEYWORD_REQUEST.getQuizRequest("루키가 작성한 것 같지만 수달이 작성한"));

        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }


    @When("{int}번 세션과 {int}번 키워드와 {int}번 퀴즈를 조회하면")
    public void 퀴즈를_조회하면(int sessionId, int keywordId, int quizId) {
        context.invokeHttpGet(
            "/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + quizId
        );
    }

    @When("{int}번 세션과 {int}번 키워드와 {int}번 퀴즈를 삭제하면")
    public void 퀴즈를_삭제하면(int sessionId, int keywordId, int quizId) {
        context.invokeHttpDelete(
            "/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + quizId
        );
    }

    @When("{int}번 세션과 {int}번 키워드와 {int}번 퀴즈 내용을 {string}으로 수정하면")
    public void 퀴즈를_수정하면(int sessionId, int keywordId, int quizId, String editedContent) {
        context.invokeHttpPut(
            "/sessions/" + sessionId + "/keywords/" + keywordId + "/quizs/" + quizId,
            KEYWORD_REQUEST.getQuizRequest(editedContent));
    }

    @Then("퀴즈가 생성된다")
    public void 퀴즈가_생성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Then("퀴즈가 조회된다")
    public void 퀴즈가_조회된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @Then("퀴즈가 수정된다")
    public void 퀴즈가_수정된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("퀴즈가 삭제된다")
    public void 퀴즈가_삭제된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
