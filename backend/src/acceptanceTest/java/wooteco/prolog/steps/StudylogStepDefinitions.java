package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG1;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG10;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG2;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG3;
import static wooteco.prolog.fixtures.StudylogAcceptanceFixture.STUDYLOG4;
import static wooteco.prolog.fixtures.TagAcceptanceFixture.TAG1;
import static wooteco.prolog.fixtures.TagAcceptanceFixture.TAG2;

import com.google.common.collect.Iterables;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.StudylogAcceptanceFixture;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogMissionRequest;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogSessionRequest;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class StudylogStepDefinitions extends AcceptanceSteps {

    @Given("스터디로그 여러개를 작성하고")
    public void 스터디로그여러개를작성하고() {
        context.invokeHttpPostWithToken("/studylogs", STUDYLOG1.getStudylogRequest());
        context.invokeHttpPostWithToken("/studylogs", STUDYLOG2.getStudylogRequest());
    }

//    @Given("스터디로그를 작성하고")
//    @When("스터디로그를 작성하면")
//    public void 스터디로그를작성하면() {
//        context.invokeHttpPostWithToken("/studylogs", STUDYLOG1.getStudylogRequest());
//    }

    @Given("스터디로그를 작성하고")
    @When("스터디로그를 작성하면")
    public void 세션과미션포함한스터디로그를작성하면() {
        StudylogRequest studylogRequest = new StudylogRequest(
            "[자바][옵셔널] 학습log 제출합니다.",
            "옵셔널은 NPE를 배제하기 위해 만들어진 자바8에 추가된 라이브러리입니다. \n " +
                "다양한 메소드를 호출하여 원하는 대로 활용할 수 있습니다",
            1L,
            1L,
            Lists.newArrayList(
                new TagRequest(TAG1.getTagName()),
                new TagRequest(TAG2.getTagName())
            ), null
        );
        context.invokeHttpPostWithToken("/studylogs", studylogRequest);

        if (context.response.statusCode() == HttpStatus.CREATED.value()) {
            context.storage.put("studylog", context.response.as(StudylogResponse.class));
        }
    }

    @When("세션과 미션 없이 스터디로그를 작성하면")
    public void 세션과미션없이스터디로그를작성하면() {
        StudylogRequest studylogRequest = new StudylogRequest(
            "[자바][옵셔널] 학습log 제출합니다.",
            "옵셔널은 NPE를 배제하기 위해 만들어진 자바8에 추가된 라이브러리입니다. \n " +
                "다양한 메소드를 호출하여 원하는 대로 활용할 수 있습니다",
            null,
            null,
            Lists.newArrayList(
                new TagRequest(TAG1.getTagName()),
                new TagRequest(TAG2.getTagName())
            ), Collections.emptyList()
        );
        context.invokeHttpPostWithToken("/studylogs", studylogRequest);
        if (context.response.statusCode() == HttpStatus.CREATED.value()) {
            context.storage.put("studylog", context.response.as(StudylogResponse.class));
        }
    }

    @Given("{string} 스터디로그를 작성하고")
    public void 특정스터디로그를작성하고(String title) {
        StudylogRequest studylogRequest = new StudylogRequest(title, "content", 1L,
            Collections.emptyList());
        context.invokeHttpPostWithToken("/studylogs", studylogRequest);
        context.storage.put(title, context.response.as(StudylogResponse.class));
    }

    @Then("스터디로그가 작성된다")
    public void 스터디로그가작성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());

        StudylogResponse studylogResponse = (StudylogResponse) context.storage.get("studylog");

        context.invokeHttpGet("/studylogs/" + studylogResponse.getId());

        StudylogResponse response = context.response.as(StudylogResponse.class);
        assertThat(response.getId()).isNotNull();
    }

    @Given("{long}개의 스터디로그를 작성하고")
    public void 다수의스터디로그를작성하면(Long totalSize) {
        for (int i = 0; i < totalSize; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG1.getStudylogRequest());
        }
    }

    @Given("{int}번 미션의 스터디로그를 {long}개 작성하고")
    public void 특정미션스터디로그를다수작성하면(int missionNumber, Long totalSize) {
        List<StudylogRequest> requests = StudylogAcceptanceFixture.findByMissionNumber(
            (long) missionNumber);

        if (requests.isEmpty()) {
            throw new RuntimeException("해당 미션의 스터디로그는 없습니다.");
        }

        for (int i = 0; i < totalSize; i++) {
            context.invokeHttpPostWithToken("/studylogs", requests.get(0));
        }
    }

    @Given("{int}번 태그의 스터디로그를 {long}개 작성하고")
    public void 특정태그스터디로그를다수작성하면(int tagNumber, Long totalSize) {
        List<StudylogRequest> requests = StudylogAcceptanceFixture.findByTagNumber(
            (long) tagNumber);

        if (requests.isEmpty()) {
            throw new RuntimeException("해당 미션의 스터디로그는 없습니다.");
        }

        for (int i = 0; i < totalSize; i++) {
            context.invokeHttpPostWithToken("/studylogs", requests.get(0));
        }
    }

    @Given("서로 다른 태그와 미션을 가진 스터디로그를 다수 생성하고")
    public void 서로다른태그와미션을가진스터디로그를생성() {
        for (int i = 0; i < 7; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG1.getStudylogRequest());
        }
        for (int i = 0; i < 5; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG2.getStudylogRequest());
        }
        for (int i = 0; i < 6; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG3.getStudylogRequest());
        }
    }

    @Given("서로 다른 세션을 가진 스터디로그를 다수 생성하고")
    public void 서로다른세션을가진스터디로그를생성() {
        for (int i = 0; i < 2; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG3.getStudylogRequest());
        }
        for (int i = 0; i < 3; i++) {
            context.invokeHttpPostWithToken("/studylogs", STUDYLOG4.getStudylogRequest());
        }
    }


    @When("{int}번 미션과 {int}번 태그로 {long}개를 조회하면")
    public void 미션태그필터를사이즈와함께조회한다(int missionNumber, int tagNumber, Long pageSize) {
        String path = String.format("/studylogs?tags=%d&missions=%d&size=%d", tagNumber,
            missionNumber, pageSize);
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

    @When("{int}번 세션의 스터디로그를 조회하면")
    public void 특정세션의스터디로그를조회하면(int sessionNumber) {
        String path = String.format("/studylogs?sessions=%d", sessionNumber);
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

    @When("로그인된 사용자가 {long}번째 스터디로그를 이미 조회한 상태로 조회하면")
    public void 로그인된사용자가스터디로그를이미조회한상태로조회하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        Map<String, String> cookies = new HashMap<>();
        cookies.put("viewed", "/" + studylogId + "/");
        context.invokeHttpGetWithTokenAndCookies(path, cookies);
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

    @Then("조회된 스터디로그의 조회수가 {int}로 증가된다")
    public void 조회된스터디로그의조회수가증가된다(int viewCount) {
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(studylog.getViewCount()).isEqualTo(viewCount);
    }

    @Then("조회된 스터디로그의 조회수가 증가되지 않고 {int}이다")
    public void 조회된스터디로그의조회수가증가되지않는다(int viewCount) {
        StudylogResponse studylog = context.response.as(StudylogResponse.class);

        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(studylog.getViewCount()).isEqualTo(viewCount);
    }

    @When("{long}번째 스터디로그를 수정하면")
    public void 스터디로그를수정하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpPutWithToken(path, STUDYLOG3.getStudylogRequest());
    }

    @When("{long}번째 스터디로그의 역량을 수정하면")
    public void 스터디로그의역량을수정하면(Long studylogId) {
        String path = "/studylogs/" + studylogId;
        context.invokeHttpPutWithToken(path, STUDYLOG10.getStudylogRequest());
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
            () -> assertThat(
                Iterables.elementsEqual(studylogs.getData(), studylogResponses)).isTrue(),
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

    @When("인기 있는 스터디로그 목록을 {string}개만큼 갱신하고")
    public void 인기있는스터디로그목록을개만큼갱신하고(String studylogCount) {
        context.invokeHttpGetWithToken("/studylogs/popular/sync?size=" + studylogCount);
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Then("인기있는 스터디로그 목록 요청시 id {string} 순서로 조회된다")
    public void 스터디로그가Id순서로조회된다(String studylogIds) {
        context.invokeHttpGet("/studylogs/popular");
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());
        PopularStudylogsResponse studylogsResponse = context.response.as(
            PopularStudylogsResponse.class);

        List<String> ids = Arrays.asList(studylogIds.split(", "));
        for (int i = 0; i < ids.size(); i++) {
            StudylogResponse response = studylogsResponse.getAllResponse().getData().get(i);
            Long id = Long.parseLong(ids.get(i));
            assertThat(response.getId()).isEqualTo(id);
        }
    }

    @When("스터디로그 세션을 {long}로 수정하면")
    public void 스터디로그세션을L로수정하면(long sessionId) {
        StudylogResponse studylogResponse = (StudylogResponse) context.storage.get("studylog");
        context.invokeHttpPutWithToken("/studylogs/" + studylogResponse.getId() + "/sessions",
            new StudylogSessionRequest(sessionId));
    }

    @Then("스터디로그 세션이 {long}로 수정된다")
    public void 스터디로그세션이로수정된다(long sessionId) {
        StudylogResponse studylogResponse = (StudylogResponse) context.storage.get("studylog");
        context.invokeHttpGet("/studylogs/" + studylogResponse.getId());
        assertThat(context.response.as(StudylogResponse.class).getSession().getId()).isEqualTo(
            sessionId);
    }

    @When("스터디로그 미션을 {long}로 수정하면")
    public void 스터디로그미션을로수정하면(long missionId) {
        StudylogResponse studylogResponse = (StudylogResponse) context.storage.get("studylog");
        context.invokeHttpPutWithToken("/studylogs/" + studylogResponse.getId() + "/missions",
            new StudylogMissionRequest(missionId));
    }

    @Then("스터디로그 미션이 {long}로 수정된다")
    public void 스터디로그미션이로수정된다(long missionId) {
        StudylogResponse studylogResponse = (StudylogResponse) context.storage.get("studylog");
        context.invokeHttpGet("/studylogs/" + studylogResponse.getId());
        assertThat(context.response.as(StudylogResponse.class).getMission().getId()).isEqualTo(
            missionId);
    }

    @Given("{long}, {long} 역량을 맵핑한 {string} 스터디로그를 작성하고")
    public void 역량을맵핑한스터디로그를작성하고(long abilityId1, long abilityId2, String studylogName) {
        StudylogRequest studylogRequest = new StudylogRequest(studylogName, "content", null, 1L,
            Collections.emptyList(), Collections.emptyList());
        context.invokeHttpPostWithToken("/studylogs", studylogRequest);
        context.storage.put(studylogName, context.response.as(StudylogResponse.class));
    }

    @Given("{long} 역량 한개를 맵핑한 {string} 스터디로그를 작성하고")
    public void 역량한개를맵핑한스터디로그를작성하고(long abilityId1, String studylogName) {
        StudylogRequest studylogRequest = new StudylogRequest(studylogName, "content", null, 1L,
            Collections.emptyList(), Collections.emptyList());
        context.invokeHttpPostWithToken("/studylogs", studylogRequest);
        context.storage.put(studylogName, context.response.as(StudylogResponse.class));
    }
}
