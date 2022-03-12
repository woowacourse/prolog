package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.report.application.dto.PageableResponse;
import wooteco.prolog.report.application.dto.ReportAbilityRequest;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;
import wooteco.prolog.report.application.dto.request.ReportRequest2;
import wooteco.prolog.report.application.dto.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.application.dto.response.SimpleReportPageableResponse;

public class ReportStepDefinitions extends AcceptanceSteps {

    private int reportCnt = 0;

    @When("리포트를 등록하(면)(고)")
    public void 리포트를등록() {
        ReportRequest reportRequest = new ReportRequest(
            "새로운 리포트" + reportCnt++,
            "리포트 설명",
            Arrays.asList(
                new ReportAbilityRequest(1L, 5),
                new ReportAbilityRequest(2L, 10),
                new ReportAbilityRequest(3L, 1),
                new ReportAbilityRequest(4L, 2)
            )
        );
        context.invokeHttpPostWithToken("/reports", reportRequest);
    }

    @When("대표 리포트를 등록하(면)(고)")
    public void 대표리포트를등록() {
        ReportRequest2 reportRequest = new ReportRequest2(
            null,
            "새로운 리포트" + reportCnt++,
            "리포트 설명",
            new GraphRequest(
                Arrays.asList(
                    new AbilityRequest(
                        1L,
                        1L,
                        true
                    )
                )
            ),
            Arrays.asList(
                new ReportStudylogRequest(
                    1L,
                    Arrays.asList(1L)
                )
            ),
            true
        );

        context.invokeHttpPostWithToken("/reports", reportRequest);
    }

    @When("리포트를 수정하면")
    public void 리포트를수정하면() {
        ReportResponse reportResponse = context.response.as(ReportResponse.class);

        ReportUpdateRequest reportUpdateRequest = new ReportUpdateRequest(
            "변경된 리포트",
            "변경된 리포트 설명1"
        );

        context.storage.put("reportRequest", reportUpdateRequest);
        context.invokeHttpPutWithToken("/reports/" + reportResponse.getId(), reportUpdateRequest);
    }

    @When("{string}의 리포트 목록을 조회하면")
    public void 리포트를조회하면(String name) {
        GithubResponses user = GithubResponses.findByName(name);
        context.invokeHttpGet("/members/{name}/reports?page=0&size=10", user.getLogin());
    }

    @When("{string}의 단순 리포트 목록을 조회하면")
    public void 단순리포트를조회하면(String name) {
        GithubResponses user = GithubResponses.findByName(name);
        context.invokeHttpGet("/reports?username={name}&type=simple&page=0&size=10", user.getLogin());
    }

    @Then("리포트 목록이 조회된다")
    public void 리포트목록이조회된다() {
        PageableResponse<ReportResponse> reportPageableResponse = context.response.as(PageableResponse.class);

        assertThat(reportPageableResponse.getCurrentPage()).isOne();
        assertThat(reportPageableResponse.getTotalPage()).isOne();
        assertThat(reportPageableResponse.getTotalSize()).isOne();
        assertThat(reportPageableResponse.getData()).hasSize(1);
    }

    @Then("단순 리포트 목록이 조회된다")
    public void 단순리포트목록이조회된다() {
        SimpleReportPageableResponse simpleReportPageableResponse =
            context.response.as(SimpleReportPageableResponse.class);

        assertThat(simpleReportPageableResponse.getCurrentPage()).isOne();
        assertThat(simpleReportPageableResponse.getTotalPage()).isOne();
        assertThat(simpleReportPageableResponse.getTotalSize()).isOne();
        assertThat(simpleReportPageableResponse.getReports()).hasSize(1);
    }

    @Then("리포트가 등록된다")
    public void 리포트가등록또는수정된다() {
        int status = context.response.statusCode();
        assertThat(status).isEqualTo(200);
    }

    @Then("리포트가 수정된다")
    public void 리포트가수정된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
