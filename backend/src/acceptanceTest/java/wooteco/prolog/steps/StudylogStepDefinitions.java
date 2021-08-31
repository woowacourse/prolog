package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG1;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG2;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.StudylogAcceptanceFixture;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

public class StudylogStepDefinitions extends AcceptanceSteps {

    @Given("스터디로그 여러개를 작성하고")
    public void 스터디르그여러개를작성하고() {
        List<StudylogRequest> studylogRequests = Arrays.asList(
                STUDYLOG1.getStudylogRequest(),
                STUDYLOG2.getStudylogRequest()
        );

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @When("스터디로그를 작성하면")
    public void 스터디로그를작성하면() {
        List<StudylogRequest> studylogRequests = Arrays.asList(
                STUDYLOG1.getStudylogRequest()
        );

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Then("스터디로그가 작성된다")
    public void 스터디로그가작성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Given("{long}개의 스터디로그를 작성하고")
    public void 다수의스터디로그를작성하면(Long totalSize) {
        List<StudylogRequest> studylogRequests = new ArrayList<>();

        for (int i = 0; i < totalSize; i++) {
            studylogRequests.add(STUDYLOG1.getStudylogRequest());
        }

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Given("{int}번 미션의 스터디로그를 {long}개 작성하고")
    public void 특정미션스터디로그를다수작성하면(int missionNumber, Long totalSize) {
        List<StudylogRequest> studylogRequests = new ArrayList<>();

        List<StudylogRequest> requests = StudylogAcceptanceFixture.findByMissionNumber(
                (long) missionNumber);

        if (requests.isEmpty()) {
            throw new RuntimeException("해당 미션의 스터디로그는 없습니다.");
        }

        for (int i = 0; i < totalSize; i++) {
            studylogRequests.add(requests.get(0));
        }

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Given("{int}번 태그의 스터디로그를 {long}개 작성하고")
    public void 특정태그스터디로그를다수작성하면(int tagNumber, Long totalSize) {
        List<StudylogRequest> studylogRequests = new ArrayList<>();

        List<StudylogRequest> requests = StudylogAcceptanceFixture.findByTagNumber(
                (long) tagNumber);

        if (requests.isEmpty()) {
            throw new RuntimeException("해당 미션의 스터디로그는 없습니다.");
        }

        for (int i = 0; i < totalSize; i++) {
            studylogRequests.add(requests.get(0));
        }

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Given("서로 다른 태그와 미션을 가진 스터디로그를 다수 생성하고")
    public void 서로다른태그와미션을가진스터디로그를생성() {
        List<StudylogRequest> studylogRequests = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            studylogRequests.add(STUDYLOG1.getStudylogRequest());
        }
        for (int i = 0; i < 5; i++) {
            studylogRequests.add(STUDYLOG2.getStudylogRequest());
        }
        for (int i = 0; i < 6; i++) {
            studylogRequests.add(STUDYLOG3.getStudylogRequest());
        }

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @When("{int}번 미션과 {int}번 태그로 조회하면")
    public void 미션태그필터조회를한다(int missionNumber, int tagNumber) {
        String path = String.format("/studylogs?tags=%d&missions=%d", tagNumber, missionNumber);
        context.invokeHttpGet(path);
    }

    @When("{int}번 미션의 스터디로그를 조회하면")
    public void 특정미션의스터디로그를조회하면(int missionNumber) {
        String path = String.format("/studylogs?missions=%d", missionNumber);
        context.invokeHttpGet(path);
    }

    @When("{int}번 태그의 스터디로그를 조회하면")
    public void 특정태그의스터디로그를조회하면(int tagNumber) {
        String path = String.format("/studylogs?tags=%d", tagNumber);
        context.invokeHttpGet(path);
    }

    @When("{long}개, {long}쪽의 페이지를 조회하면")
    public void 스터디로그페이지를조회하면(Long pageSize, Long pageNumber) {
        String path = String.format("/studylogs?page=%d&size=%d", pageNumber, pageSize);
        context.invokeHttpGet(path);
    }

    @Then("{int}개의 스터디로그 목록을 받는다")
    public void 다수의스터디로그목록을받는다(int pageSize) {
        StudylogsResponse studylogs = context.response.as(StudylogsResponse.class);

        assertThat(studylogs.getData().size()).isEqualTo(pageSize);
    }

    @When("스터디로그 목록을 조회하면")
    public void 스터디로그목록을조회하면() {
        context.invokeHttpGet("/studylogs");
    }

    @Then("스터디로그 목록을 받는다")
    public void 스터디로그목록을받는다() {
        StudylogsResponse studylogs = context.response.as(StudylogsResponse.class);

        assertThat(studylogs.getData().size()).isEqualTo(2);
    }

    @When("{long}번째 스터디로그를 조회하면")
    public void 스터디로그를조회하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpGet(path);
    }

    @Then("{long}번째 스터디로그가 조회된다")
    public void 스터디로그가조회된다(Long studylogId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        String path = "/studylogs/" + studylogId;
        context.invokeHttpGet(path);
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(studylog).isNotNull();
    }

    @When("{long}번째 스터디로그를 수정하면")
    public void 스터디로그를수정하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpPutWithToken(path, STUDYLOG3.getStudylogRequest());
    }

    @Then("{long}번째 스터디로그가 수정된다")
    public void 스터디로그가수정된다(Long studylogId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        String path = "/studylogs/" + studylogId;
        context.invokeHttpGet(path);
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(studylog.getContent()).isEqualTo(STUDYLOG3.getStudylogRequest().getContent());
    }

    @Then("에러 응답을 받는다")
    public void 에러가응답을받는다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @When("{long}번째 스터디로그를 삭제하면")
    public void 스터디로그를삭제하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpDeleteWithToken(path);
    }

    @Then("스터디로그가 삭제된다")
    public void 스터디로그가삭제된다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
