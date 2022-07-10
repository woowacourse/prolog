package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.PopularStudylogsResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.StudylogResponse;
import wooteco.prolog.studylog.application.dto.StudylogsResponse;
import wooteco.prolog.studylog.application.dto.TagRequest;

class StudylogDocumentation extends Documentation {

    @Test
    void 스터디로그를_생성한다() {
        // when
        ExtractableResponse<Response> createResponse = given("studylog/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(createStudylogRequest1())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/studylogs")
            .then().log().all().extract();

        // then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createResponse.header("Location")).isNotNull();
    }

    @Test
    void 스터디로그_단건을_조회한다() {
        // given
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest1());
        String location = studylogResponse.header("Location");

        given("studylog/read")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get(location)
            .then().log().all().extract();
    }

    @Test
    void 스터디로그_목록을_조회한다() {
        // given
        String title1 = 스터디로그_등록함(createStudylogRequest1()).as(StudylogResponse.class).getTitle();
        String title2 = 스터디로그_등록함(createStudylogRequest2()).as(StudylogResponse.class).getTitle();

        // when
        ExtractableResponse<Response> response = given("studylog/list")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/studylogs")
            .then().log().all().extract();

        // then
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        List<String> studylogsTitles = studylogsResponse.getData().stream()
            .map(StudylogResponse::getTitle)
            .collect(Collectors.toList());
        List<String> expectedTitles = Arrays.asList(title1, title2).stream()
            .sorted()
            .collect(Collectors.toList());
        assertThat(studylogsTitles).usingRecursiveComparison().isEqualTo(expectedTitles);
    }

    @Disabled
    @Test
    void 스터디로그_목록을_검색_및_필터링한다() {
        // given
        스터디로그_등록함(createStudylogRequest1());
        스터디로그_등록함(createStudylogRequest2());

        // when
        ExtractableResponse<Response> response = given("studylog/filter")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get(
                "/posts?keyword=joanne&sessions=1&missions=1&tags=1&tags=2&usernames=soulG&startDate=20210901&endDate=20211231")
            .then().log().all().extract();

        // given
        StudylogsResponse studylogsResponse = response.as(StudylogsResponse.class);
        assertThat(studylogsResponse.getData()).hasSize(1);
    }

    @Test
    void 인기있는_스터디로그_목록을_갱신하고_조회한다() {
        // given
        String studylogLocation1 = 스터디로그_등록함(createStudylogRequest1()).header("Location");
        String studylogLocation2 = 스터디로그_등록함(createStudylogRequest2()).header("Location");
        Long studylogId1 = Long.parseLong(studylogLocation1.split("/studylogs/")[1]);
        Long studylogId2 = Long.parseLong(studylogLocation2.split("/studylogs/")[1]);

        스터디로그_단건_조회(studylogId1);
        스터디로그_단건_조회(studylogId2);
        스터디로그_단건_좋아요(studylogId2);

        인기있는_스터디로그_목록_갱신(2);

        // when
        ExtractableResponse<Response> response = given("studylogs/popular")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get("/studylogs/popular")
            .then().log().all().extract();

        // then
        PopularStudylogsResponse popularStudylogsResponse = response.as(
            PopularStudylogsResponse.class);
        assertThat(popularStudylogsResponse.getAllResponse().getData()).hasSize(2);
        assertThat(popularStudylogsResponse.getAllResponse().getData().get(0).getStudylogResponse()
            .getId()).isEqualTo(studylogId2);
        assertThat(popularStudylogsResponse.getAllResponse().getData().get(1).getStudylogResponse()
            .getId()).isEqualTo(studylogId1);
    }


    private void 인기있는_스터디로그_목록_갱신(int studylogCount) {
        RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(PageRequest.of(1, studylogCount))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/studylogs/popular/sync")
            .then().log().all()
            .extract();
    }

    @Test
    void 스터디로그를_수정한다() {
        // given
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest1());
        String location = studylogResponse.header("Location");

        String title = "수정된 제목";
        String content = "수정된 내용";

        Long sessionId = 세션_등록함(new SessionRequest("세션2"));
        Long missionId = 미션_등록함(new MissionRequest("수정된 미션", sessionId));
        List<TagRequest> tags = Arrays.asList(
            new TagRequest("spa"),
            new TagRequest("edit")
        );
        StudylogRequest editStudylogRequest = new StudylogRequest(title, content, missionId, tags);

        // when
        ExtractableResponse<Response> editResponse = given("studylog/edit")
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
    void 스터디로그를_삭제한다() {
        ExtractableResponse<Response> studylogResponse = 스터디로그_등록함(createStudylogRequest1());
        String location = studylogResponse.header("Location");

        given("studylog/delete")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete(location);
    }

    @Test
    void 스터지로그를_좋아요한다() {
        ExtractableResponse<Response> response = 스터디로그_등록함(createStudylogRequest1());
        long studylogId = Long.parseLong(response.header("Location").split("/studylogs/")[1]);

        given("studylog/like")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().post("/studylogs/" + studylogId + "/likes")
            .then().log().all();
    }

    @Test
    void 스터지로그를_좋아요_취소한다() {
        ExtractableResponse<Response> response = 스터디로그_등록함(createStudylogRequest1());
        long studylogId = Long.parseLong(response.header("Location").split("/studylogs/")[1]);

        스터디로그_단건_좋아요(studylogId);

        given("studylog/unlike")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().delete("/studylogs/" + studylogId + "/likes")
            .then().log().all();
    }

    private StudylogRequest createStudylogRequest1() {
        String title = "나는야 Joanne";
        String content = "SPA 방식으로 앱을 구현하였음.\n" + "router 를 구현 하여 이용함.\n";
        Long sessionId = 세션_등록함(new SessionRequest("프론트엔드JS 레벨1 - 2021"));
        Long missionId = 미션_등록함(new MissionRequest("세션1 - 지하철 노선도 미션", sessionId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("spa"), new TagRequest("router"));

        return new StudylogRequest(title, content, sessionId, missionId, tags);
    }

    private StudylogRequest createStudylogRequest2() {
        String title = "JAVA";
        String content = "Spring Data JPA를 학습함.";
        Long sessionId = 세션_등록함(new SessionRequest("백엔드Java 레벨1 - 2021"));
        Long missionId = 미션_등록함(new MissionRequest("세션3 - 프로젝트", sessionId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("java"), new TagRequest("jpa"));

        return new StudylogRequest(title, content, sessionId, missionId, tags);
    }

    private ExtractableResponse<Response> 스터디로그_등록함(StudylogRequest request) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/studylogs")
            .then().log().all().extract();
    }

    private Long 세션_등록함(SessionRequest request) {
        return RestAssured
            .given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/sessions")
            .then()
            .log().all()
            .extract()
            .as(SessionResponse.class)
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

    private void 스터디로그_단건_좋아요(Long studylogId) {
        RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().post("/studylogs/" + studylogId + "/likes")
            .then().log().all().extract();
    }

    private void 스터디로그_단건_조회(Long studylogId) {
        RestAssured.given()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().get("/studylogs/" + studylogId)
            .then().log().all().extract();
    }
}
