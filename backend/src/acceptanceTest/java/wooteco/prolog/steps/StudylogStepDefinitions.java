package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG1;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG2;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG3;

import com.google.common.collect.Iterables;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.StudylogAcceptanceFixture;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;

public class StudylogStepDefinitions extends AcceptanceSteps {

    @Given("스터디로그 여러개를 작성하고")
    public void 스터디로그여러개를작성하고() {
        List<StudylogRequest> studylogRequests = Arrays.asList(
            STUDYLOG1.getStudylogRequest(),
            STUDYLOG2.getStudylogRequest()
        );

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Given("스터디로그를 작성하고")
    @When("스터디로그를 작성하면")
    public void 스터디로그를작성하면() {
        List<StudylogRequest> studylogRequests = Arrays.asList(
            STUDYLOG1.getStudylogRequest()
        );

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }

    @Given("{string} 스터디로그를 작성하고")
    public void 특정스터디로그를작성하고(String title) {
        List<StudylogRequest> studylogRequests = Arrays.asList(
            new StudylogRequest(title, "content", 1L, Collections.emptyList())
        );

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
        context.storage.put(title, context.response.as(StudylogResponse.class));
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

    @Given("서로 다른 레벨을 가진 스터디로그를 다수 생성하고")
    public void 서로다른레벨을가진스터디로그를생성() {
        List<StudylogRequest> studylogRequests = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            studylogRequests.add(STUDYLOG1.getStudylogRequest());
        }
        for (int i = 0; i < 3; i++) {
            studylogRequests.add(STUDYLOG2.getStudylogRequest());
        }
        for (int i = 0; i < 4; i++) {
            studylogRequests.add(STUDYLOG3.getStudylogRequest());
        }

        context.invokeHttpPostWithToken("/studylogs", studylogRequests);
    }


    @When("{int}번 미션과 {int}번 태그로 {long}개를 조회하면")
    public void 미션태그필터를사이즈와함께조회한다(int missionNumber, int tagNumber, Long pageSize) {
        String path = String.format("/studylogs?tags=%d&missions=%d&size=%d", tagNumber, missionNumber, pageSize);
        context.invokeHttpGet(path);
    }

    @When("{int}번 미션과 {int}번 태그로 조회하면")
    public void 미션태그를조회한다(int missionNumber, int tagNumber) {
        String path = String.format("/studylogs?tags=%d&missions=%d", tagNumber, missionNumber);
        context.invokeHttpGet(path);
    }

    @When("{string}(을)(를) 검색하면")
    public void 을검색하면(String keyword) {
        String path = String.format("/studylogs?keyword=%s", keyword);
        context.invokeHttpGet(path);
    }

    @When("{string}을 검색하고 {int}번 태그의 스터디로그를 조회하면")
    public void 을검색하고번태그의스터디로그를조회하면(String keyword, int tagId) {
        String path = String.format("/studylogs?keyword=%s&tags=%d", keyword, tagId);
        context.invokeHttpGet(path);
    }

    @When("{string}을 검색하고 {int}번 미션과 {int}번 태그로 조회하면")
    public void 을검색하고번미션과번태그로조회하면(String keyword, int missionNumber, int tagId) {
        String path = String.format("/studylogs?keyword=%s&missions=%d&tags=%d", keyword,
                                    missionNumber, tagId);
        context.invokeHttpGet(path);
    }

    @When("{int}번 레벨의 스터디로그를 조회하면")
    public void 특정레벨의스터디로그를조회하면(int levelNumber) {
        String path = String.format("/studylogs?levels=%d", levelNumber);
        context.invokeHttpGet(path);
    }

    @When("{int}번 미션의 스터디로그를 조회하면")
    public void 특정미션의스터디로그를조회하면(int missionNumber) {
        String path = String.format("/studylogs?missions=%d", missionNumber);
        context.invokeHttpGet(path);
    }

    @When("총 {long}개, {int}번 태그의 스터디로그를 조회하면")
    public void 특정태그의스터디로그를조회하면(Long pageSize, int tagNumber) {
        String path = String.format("/studylogs?tags=%d&size=%d", tagNumber, pageSize);
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

    @When("로그인된 사용자가 {long}번째 스터디로그를 조회하면")
    public void 로그인된사용자가스터디로그를조회하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpGetWithToken(path);
    }

    @When("id {string} 스터디로그를 조회하면")
    public void 여러개의스터디로그를조회하면(String studylogIds) {
        List<String> ids = Arrays.asList(studylogIds.split(","));
        String path = "/studylogs?ids=" + String.join(",", ids);
        context.invokeHttpGet(path);
    }

    @Then("id {string} 스터디로그가 조회된다")
    public void 여러개의스터디로그가조회된다(String studylogIds) {
        List<String> ids = Arrays.asList(studylogIds.split(","));
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        StudylogsResponse studylogsResponse = context.response.as(StudylogsResponse.class);
        assertThat(studylogsResponse.getData()).hasSize(ids.size());
    }

    @Then("{long}번째 스터디로그가 조회된다")
    public void 스터디로그가조회된다(Long studylogId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        String path = "/studylogs/" + studylogId;
        context.invokeHttpGet(path);
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(studylog).isNotNull();
    }

    @Then("조회된 스터디로그의 조회수가 증가된다")
    public void 조회된스터디로그의조회수가증가된다() {
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(studylog.getViewCount()).isEqualTo(1);
    }

    @Then("조회된 스터디로그의 조회수가 증가되지 않는다")
    public void 조회된스터디로그의조회수가증가되지않는다() {
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(studylog.getViewCount()).isEqualTo(0);
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

    @Then("{int}개의 스터디로그를 id의 역순으로 받는다")
    public void 개의스터디로그를Id의역순으로받는다(int pageSize) {
        StudylogsResponse studylogs = context.response.as(StudylogsResponse.class);

        List<StudylogResponse> studylogResponses = new ArrayList<>(studylogs.getData());
        studylogResponses.sort((o1, o2) -> {
            if (o1.getCreatedAt().isAfter(o2.getCreatedAt())) {
                return -1;
            } else if (o1.getCreatedAt().equals(o2.getCreatedAt())) {
                return 0;
            }
            return 1;
        });

        assertAll(
            () -> assertThat(Iterables.elementsEqual(studylogs.getData(), studylogResponses)).isTrue(),
            () -> assertThat(studylogs.getData().size()).isEqualTo(pageSize)
        );
    }

    @When("로그인된 사용자가 {long}번째 스터디로그를 좋아요 하(면)(고)")
    public void 로그인된사용자가스터디로그를좋아요(Long studylogId) {
        String path = "/studylogs/" + studylogId + "/likes";
        context.invokeHttpPostWithToken(path);
    }

    @When("로그인된 사용자가 {long}번째 스터디로그를 좋아요 취소하(면)(고)")
    public void 로그인된사용자가스터디로그를좋아요취소(Long studylogId) {
        String path = "/studylogs/" + studylogId + "/likes";
        context.invokeHttpDeleteWithToken(path);
    }

    @Then("조회된 스터디로그의 좋아요 수가 증가한다")
    public void 조회된스터디로그의좋아요수가증가한다() {
        StudylogResponse response = context.response.as(StudylogResponse.class);
        assertThat(response.getLikesCount()).isEqualTo(1);
    }

    @Then("조회된 스터디로그의 좋아요 수가 증가하지 않는다")
    public void 조회된스터디로그의좋아요수가증가하지않는다() {
        StudylogResponse response = context.response.as(StudylogResponse.class);
        assertThat(response.getLikesCount()).isEqualTo(0);
    }

    @Then("조회된 스터디로그의 좋아요 여부가 참이다")
    public void 조회된스터디로그의좋아요여부가참이다() {
        StudylogResponse response = context.response.as(StudylogResponse.class);
        assertThat(response.isLiked()).isTrue();
    }

    @Then("조회된 스터디로그의 좋아요 여부가 거짓이다")
    public void 조회된스터디로그의좋아요여부가거짓이다() {
        StudylogResponse response = context.response.as(StudylogResponse.class);
        assertThat(response.isLiked()).isFalse();
    }

    @Then("인기있는 스터디로그 목록 요청시 id {string} 순서로 조회된다")
    public void 스터디로그가Id순서로조회된다(String studylogIds) {
        context.invokeHttpGet("/studylogs/most-popular");
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        StudylogsResponse studylogsResponse = context.response.as(StudylogsResponse.class);

        List<String> ids = Arrays.asList(studylogIds.split(", "));
        for (int i = 0; i < ids.size(); i++) {
            StudylogResponse response = studylogsResponse.getData().get(i);
            Long id = Long.parseLong(ids.get(i));
            assertThat(response.getId()).isEqualTo(id);
        }
    }
}
