package wooteco.prolog.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.LevellogFixture;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummariesResponse;
import wooteco.prolog.levellogs.application.dto.LevelLogSummaryResponse;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionResponse;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpHeaders.LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wooteco.prolog.fixtures.LevellogFixture.LEVEL_LOG_UPDATE_REQUEST;
import static wooteco.prolog.fixtures.LevellogFixture.levelLogRequests;

public class LevellogsStepDefinitions extends AcceptanceSteps {

    @When("레벨로그를 작성하(고)(면)")
    public void 레벨로그를작성하면() {
        context.invokeHttpPostWithToken("/levellogs", LevellogFixture.LEVEL_LOG_REQUEST);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        String levelLogId = context.response.getHeader(LOCATION).replaceAll("/levellogs/", "");
        context.storage.put("levelLogId", Long.valueOf(levelLogId));
    }

    @Then("레벨로그가 조회된다")
    public void 레벨로그가조회된다() {
        Object levelLogId = context.storage.get("levelLogId");
        context.invokeHttpGet("/levellogs/" + levelLogId);

        LevelLogResponse response = context.response.as(LevelLogResponse.class);

        assertAll(
            () -> assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.getTitle()).isEqualTo("title1"),
            () -> assertThat(response.getContent()).isEqualTo("content1"),
            () -> assertThat(response.getAuthor().getNickname()).isEqualTo("브라운"),
            () -> assertThat(response.getLevelLogs())
                .extracting(SelfDiscussionResponse::getQuestion, SelfDiscussionResponse::getAnswer)
                .containsExactlyInAnyOrder(
                    tuple("Q1", "A1"),
                    tuple("Q2", "A2")
                )
        );
    }

    @And("레벨로그를 삭제하면")
    public void 레벨로그를삭제하면() {
        Object levelLogId = context.storage.get("levelLogId");
        context.invokeHttpDeleteWithToken("/levellogs/" + levelLogId);

        assertThat(context.response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("레벨로그가 삭제된다")
    public void 레벨로그가삭제된다() {
        Object levelLogId = context.storage.get("levelLogId");
        context.invokeHttpGet("/levellogs/" + levelLogId);

        assertThat(context.response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @And("레벨로그를 수정하면")
    public void 레벨로그를수정하면() {
        Object levelLogId = context.storage.get("levelLogId");
        context.invokeHttpPutWithToken("/levellogs/" + levelLogId, LEVEL_LOG_UPDATE_REQUEST);

        assertThat(context.response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("레벨로그가 수정된다")
    public void 레벨로그가수정된다() {
        Object levelLogId = context.storage.get("levelLogId");
        context.invokeHttpGet("/levellogs/" + levelLogId);

        LevelLogResponse response = context.response.as(LevelLogResponse.class);

        assertAll(
            () -> assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.getTitle()).isEqualTo("updated title"),
            () -> assertThat(response.getContent()).isEqualTo("updated content"),
            () -> assertThat(response.getAuthor().getNickname()).isEqualTo("브라운"),
            () -> assertThat(response.getLevelLogs())
                .extracting(SelfDiscussionResponse::getQuestion, SelfDiscussionResponse::getAnswer)
                .containsExactlyInAnyOrder(
                    tuple("Updated Q1", "Updated A1"),
                    tuple("Updated Q2", "Updated A2")
                )
        );
    }

    @When("레벨로그를 여러개 작성하면")
    public void 레벨로그를여러개작성하면() {
        List<Long> levelLogIds = new ArrayList<>();

        for (LevelLogRequest request : levelLogRequests()) {
            context.invokeHttpPostWithToken("/levellogs", request);
            assertThat(context.response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            String levelLogId = context.response.getHeader(LOCATION).replaceAll("/levellogs/", "");
            levelLogIds.add(Long.valueOf(levelLogId));
        }

        context.storage.put("levelLogIds", levelLogIds);
    }

    @Then("레벨로그가 여러개 조회된다")
    public void 레벨로그가여러개조회된다() {
        context.invokeHttpGet("/levellogs?page=0&size=3");

        LevelLogSummariesResponse response = context.response.as(LevelLogSummariesResponse.class);

        List<Long> levelLogIds = (List<Long>) context.storage.get("levelLogIds");

        assertAll(
            () -> assertThat(context.response.getStatusCode()).isEqualTo(HttpStatus.OK.value()),
            () -> assertThat(response.getCurrPage()).isEqualTo(1),
            () -> assertThat(response.getTotalPage()).isEqualTo(2),
            () -> assertThat(response.getTotalSize()).isEqualTo(5),
            () -> assertThat(response.getData())
                .extracting(LevelLogSummaryResponse::getId)
                .containsExactlyInAnyOrderElementsOf(levelLogIds.subList(2, 5))
        );
    }
}
