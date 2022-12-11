package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.CurriculumFixture.커리큘럼1_생성_요청_DTO;
import static wooteco.prolog.fixtures.CurriculumFixture.커리큘럼_수정_요청_DTO;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;

public class CurriculumStepDefinitions extends AcceptanceSteps {

    @When("커리큘럼을 생성하고")
    @When("커리큘럼을 생성하면")
    public void 커리큘럼을_생성하면() {
        context.invokeHttpPostWithToken("/curriculums", 커리큘럼1_생성_요청_DTO());
    }

    @Then("커리큘럼이 생성된다")
    public void 커리큘럼이_생성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("커리쿨럼을 조회하면")
    public void 커리쿨럼을_조회하면() {
        context.invokeHttpGetWithToken("/curriculums");
    }

    @Then("커리큘럼이 조회된다")
    public void 커리큘럼이_조회된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
    }

    @When("{int}번 커리쿨럼을 수정하면")
    public void 커리큘럼을_수정하면(int curriculumId) {
        context.invokeHttpPutWithToken("/curriculums/" + curriculumId, 커리큘럼_수정_요청_DTO());
    }

    @Then("커리큘럼이 삭제된다")
    @Then("커리큘럼이 수정된다")
    public void 커리큘럼이_수정_삭제된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @When("{int}번 커리쿨럼을 삭제하면")
    public void 커리큘럼을_삭제하면(int curriculumId) {
        context.invokeHttpDeleteWithToken("/curriculums/" + curriculumId);
    }
}
