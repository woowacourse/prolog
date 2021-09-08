package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.level.application.dto.LevelRequest;
import wooteco.prolog.level.application.dto.LevelResponse;
import wooteco.prolog.mission.application.dto.MissionRequest;
import wooteco.prolog.mission.application.dto.MissionResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class StudylogDocumentation extends Documentation {

    @Test
    public void 포스트를_생성한다() {
        // given
        StudylogRequest post = createPostRequest1();
        List<StudylogRequest> studylogRequest = Arrays.asList(post);

        // when
        ExtractableResponse<Response> createResponse = given("post/create")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(studylogRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/posts")
                .then().log().all().extract();

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isNotNull();
    }

    @Test
    public void 포스트_단건을_조회한다() {
        // given
        ExtractableResponse<Response> postResponse = 포스트_등록함(Arrays.asList(createPostRequest1()));
        String location = postResponse.header("Location");

        given("post/read")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .when().get(location)
                .then().log().all().extract();
    }

    @Test
    public void 포스트_목록을_조회한다() {
        // given
        List<StudylogRequest> studylogRequests = Arrays.asList(createPostRequest1(), createPostRequest2());
        포스트_등록함(studylogRequests);

        // when
        ExtractableResponse<Response> response = given("post/list")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts")
                .then().log().all().extract();

        // then
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        List<String> postTitles = studylogsResponse.getData().stream()
                .map(StudylogResponse::getTitle)
                .collect(Collectors.toList());
        List<String> expectedTitles = studylogRequests.stream()
                .map(StudylogRequest::getTitle)
                .sorted()
                .collect(Collectors.toList());
        assertThat(postTitles).usingRecursiveComparison().isEqualTo(expectedTitles);
    }

    @Test
    public void 포스트_목록을_필터링한다() {
        // given
        포스트_등록함(Arrays.asList(createPostRequest1(), createPostRequest2()));

        // when
        ExtractableResponse<Response> response = given("post/filter")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/posts?levels=1&missions=1&tags=1&tags=2&usernames=soulG")
                .then().log().all().extract();

        // given
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        assertThat(studylogsResponse.getData()).hasSize(1);
    }

    @Test
    public void 포스트를_수정한다() {
        // given
        ExtractableResponse<Response> postResponse = 포스트_등록함(Arrays.asList(createPostRequest1()));
        String location = postResponse.header("Location");

        String title = "수정된 제목";
        String content = "수정된 내용";

        Long levelId = 레벨_등록함(new LevelRequest("레벨2"));
        Long missionId = 미션_등록함(new MissionRequest("수정된 미션", levelId));
        List<TagRequest> tags = Arrays.asList(
                new TagRequest("spa"),
                new TagRequest("edit")
        );
        StudylogRequest editStudylogRequest = new StudylogRequest(title, content, missionId, tags);

        // when
        ExtractableResponse<Response> editResponse = given("post/edit")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(editStudylogRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(location)
                .then().log().all()
                .extract();

        // then
        assertThat(editResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void 포스트를_삭제한다() {
        ExtractableResponse<Response> postResponse = 포스트_등록함(Arrays.asList(createPostRequest1()));
        String location = postResponse.header("Location");

        given("post/delete")
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().delete(location);
    }

    private StudylogRequest createPostRequest1() {
        String title = "SPA";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long levelId = 레벨_등록함(new LevelRequest("레벨1"));
        Long missionId = 미션_등록함(new MissionRequest("레벨1 - 지하철 노선도 미션", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private StudylogRequest createPostRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long levelId = 레벨_등록함(new LevelRequest("레벨3"));
        Long missionId = 미션_등록함(new MissionRequest("레벨3 - 프로젝트", levelId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("java"), new TagRequest("jpa"));

        return new StudylogRequest(title, content, missionId, tags);
    }

    private ExtractableResponse<Response> 포스트_등록함(List<StudylogRequest> request) {
        return RestAssured.given().log().all()
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().log().all()
                .post("/posts")
                .then().log().all().extract();
    }

    private Long 레벨_등록함(LevelRequest request) {
        return RestAssured
                .given().log().all()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/levels")
                .then()
                .log().all()
                .extract()
                .as(LevelResponse.class)
                .getId();
    }

    private Long 미션_등록함(MissionRequest request) {
        return RestAssured.given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/missions")
                .then()
                .log().all()
                .extract()
                .as(MissionResponse.class)
                .getId();
    }
}
