package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.member.ui.StudyLogOverviewController.MemberPostResponse;
import wooteco.prolog.member.ui.StudyLogOverviewController.MemberTagResponse;

public class StudyLogOverviewStepDefinitions extends AcceptanceSteps {

    @When("{string}의 태그 목록을 조회하면")
    public void 나의태그목록을조회하면(String name) {
        context.invokeHttpGet("/members/" + name + "/tags");
    }

    @Then("해당 유저의 태그 목록이 조회된다")
    public void 나의태그목록이조회된다() {
        final List<MemberTagResponse> data = context.response.then().extract()
                .body()
                .jsonPath()
                .getList("data", MemberTagResponse.class);

        //TODO: api 완료 후 Fixture 를 이용해 더 디테일한 검증 테스트 필요
        assertThat(data).isNotNull();
    }

    @When("{string}의 {int}년 {int}월 포스트 목록을 조회하면")
    public void 나의포스트목록을조회하면(String name, int year, int month) {
        String path = "members/" + name + "/calendar-posts?year="+year+"&month=" + month;
        context.invokeHttpGet(path);
    }

    @Then("해당 유저의 포스트 목록이 조회된다")
    public void 나의포스트목록이조회된다() {
        final List<MemberPostResponse> data = context.response.then().extract()
                .body()
                .jsonPath()
                .getList("data", MemberPostResponse.class);

        //TODO: api 완료 후 Fixture 를 이용해 더 디테일한 검증 테스트 필요
        assertThat(data).isNotNull();
    }
}
