package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.common.PageableResponse;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.report.application.dto.ReportAbilityRequest;
import wooteco.prolog.report.application.dto.ReportRequest;
import wooteco.prolog.report.application.dto.ReportResponse;
import wooteco.prolog.report.application.dto.ReportUpdateRequest;

public class ReportStepDefinitions extends AcceptanceSteps {

    private int reportCnt = 0;

    @When("리포트를 등록하(면)(고)")
    public void 리포트를등록() {
        ReportRequest reportRequest = new ReportRequest(
            "새로운 리포트" + reportCnt++,
            "리포트 설명",
            LocalDate.now().minusDays(7).toString(),
            LocalDate.now().toString(),
            Arrays.asList(
                new ReportAbilityRequest(1L, 5),
                new ReportAbilityRequest(2L, 10),
                new ReportAbilityRequest(3L, 1),
                new ReportAbilityRequest(4L, 2)
            )
        );
        context.invokeHttpPostWithToken("/reports", reportRequest);
        if (context.response.statusCode() == HttpStatus.CREATED.value()) {
            context.storage.put("report", context.response.as(ReportResponse.class));
        }
    }

    @When("리포트를 수정하면")
    public void 리포트를수정하면() {
        ReportResponse report = (ReportResponse) context.storage.get("report");

        ReportUpdateRequest reportUpdateRequest = new ReportUpdateRequest(
            "변경된 리포트",
            "변경된 리포트 설명1"
        );

        context.storage.put("reportRequest", reportUpdateRequest);
        context.invokeHttpPutWithToken("/reports/" + report.getId(), reportUpdateRequest);
    }

    @When("리포트를 삭제하면")
    public void 리포트를삭제하면() {
        ReportResponse report = (ReportResponse) context.storage.get("report");

        context.invokeHttpDeleteWithToken("/reports/" + report.getId());
    }

    @When("{string}의 리포트 목록을 조회하면")
    public void 리포트를조회하면(String name) {
        GithubResponses user = GithubResponses.findByName(name);
        context.invokeHttpGet("/members/{name}/reports?page=0&size=10", user.getLogin());
    }

    @When("{long}번째 리포트를 조회하면")
    public void 리포트를조회하면(Long reportId) {
        context.invokeHttpGet("/reports/{reportId}", reportId);
    }

    @Then("리포트 목록이 조회된다")
    public void 리포트목록이조회된다() {
        PageableResponse<ReportResponse> reportPageableResponse = context.response.as(PageableResponse.class);

        assertThat(reportPageableResponse.getCurrentPage()).isOne();
        assertThat(reportPageableResponse.getTotalPage()).isOne();
        assertThat(reportPageableResponse.getTotalSize()).isOne();
        assertThat(reportPageableResponse.getData()).hasSize(1);
    }

    @Then("리포트가 조회된다")
    public void 리포트가조회된다() {
        ReportResponse reportResponse = context.response.as(ReportResponse.class);

        assertThat(reportResponse.getId()).isNotNull();
    }

    @Then("리포트가 등록된다")
    public void 리포트가등록또는수정된다() {
        int status = context.response.statusCode();
        assertThat(status).isEqualTo(HttpStatus.OK.value());
    }

    @Then("리포트가 수정된다")
    public void 리포트가수정된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Then("리포트가 삭제된다")
    public void 리포트가삭제된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("리포트 목록에서 제거된다")
    public void 리포트목록에서제거된다() {
        String username = (String) context.storage.get("username");
        context.invokeHttpGetWithToken("/members/" + username + "/reports");

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReportResponse report = (ReportResponse) context.storage.get("report");
        List<Long> reportIds = context.response.jsonPath().getList("id");

        assertThat(reportIds).isNull();
    }
}
