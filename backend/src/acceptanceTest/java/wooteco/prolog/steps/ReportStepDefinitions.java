package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Arrays;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.report.application.dto.report.request.ReportRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.AbilityRequest;
import wooteco.prolog.report.application.dto.report.request.abilitigraph.GraphRequest;
import wooteco.prolog.report.application.dto.report.request.studylog.ReportStudylogRequest;
import wooteco.prolog.report.application.dto.report.response.ReportResponse;

public class ReportStepDefinitions extends AcceptanceSteps {

    @When("리포트를 등록하(면)(고)")
    public void 리포트를등록() {
        ReportRequest reportRequest = new ReportRequest(
            null,
            "새로운 리포트",
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

        ReportRequest reportRequest = new ReportRequest(
            reportResponse.getId(),
            "변경된 리포트",
            "변경된 리포트 설명1",
            new GraphRequest(
                Arrays.asList(
                    new AbilityRequest(
                        3L,
                        1L,
                        true
                    )
                )
            ),
            Arrays.asList(
                new ReportStudylogRequest(
                    2L,
                    Arrays.asList(3L)
                )
            ),
            true
        );

        context.invokeHttpPutWithToken("/reports/" + reportResponse.getId(), reportRequest);
    }

    @Then("리포트가 등록된다")
    public void 리포트가등록또는수정된다() {
        int status = context.response.statusCode();
        assertThat(status).isEqualTo(200);
    }

    @Then("리포트가 수정된다")
    public void 리포트가수정된다() {
        int status = context.response.statusCode();
        assertThat(status).isEqualTo(200);

        ReportResponse reportResponse = context.response.as(ReportResponse.class);

//        assertThat(reportResponse)
//            .usingRecursiveComparison()
//            .isEqualTo(reportRequest);

    }
}
