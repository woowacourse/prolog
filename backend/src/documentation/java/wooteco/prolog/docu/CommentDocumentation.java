package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.session.application.dto.MissionRequest;
import wooteco.prolog.session.application.dto.MissionResponse;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentsResponse;
import wooteco.prolog.studylog.application.dto.StudylogRequest;
import wooteco.prolog.studylog.application.dto.TagRequest;

public class CommentDocumentation extends Documentation {

    @Test
    void 댓글을_등록한다() {
        // given
        Long studylogId = 스터디로그_등록함(createStudylogRequest());

        // when
        ExtractableResponse<Response> extract = given("comment/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(createCommentRequest())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/studylogs/" + studylogId + "/comments")
            .then().log().all().extract();

        // then
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(extract.header("Location")).isNotNull();
    }

    @Test
    void 단일_스터디로그에_대한_댓글을_조회한다() {
        // given
        Long studylogId = 스터디로그_등록함(createStudylogRequest());
        댓글_등록함(studylogId, createCommentRequest());

        // when
        ExtractableResponse<Response> extract = given("comment/showComment")
            .body(createCommentRequest())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/studylogs/" + studylogId + "/comments")
            .then().log().all().extract();

        // then
        CommentsResponse commentsResponse = extract.as(CommentsResponse.class);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(commentsResponse.getData())
            .usingRecursiveComparison()
            .ignoringFields("createAt").isEqualTo(org.elasticsearch.common.collect.List.of(
                new CommentResponse(1L, new CommentMemberResponse(1L, GithubResponses.소롱.getName(), GithubResponses.소롱.getAvatarUrl()), "댓글의 내용입니다.", null)
            ));
    }

    private StudylogRequest createStudylogRequest() {
        String title = "스터디로그 제목";
        String content = "스터디로그에 본문 내용임.\n" + "여기에 글을 작성할 수 있음\n";
        Long sessionId = 세션_등록함(new SessionRequest("백엔드Java 레벨1 - 2022"));
        Long missionId = 미션_등록함(new MissionRequest("[BE] 레벨1 - 미션이름", sessionId));
        List<TagRequest> tags = Arrays.asList(new TagRequest("tag"));

        return new StudylogRequest(title, content, sessionId, missionId, tags);
    }

    private CommentCreateRequest createCommentRequest() {
        return new CommentCreateRequest("댓글의 내용입니다.");
    }

    private Long 스터디로그_등록함(StudylogRequest request) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/studylogs")
            .then().log().all().extract();

        return Long.parseLong(extract.header("Location").split("/studylogs/")[1]);
    }

    private void 댓글_등록함(Long studylogId, CommentCreateRequest request) {
        RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/studylogs/" + studylogId + "/comments")
            .then().log().all().extract();
    }

    private Long 세션_등록함(SessionRequest request) {
        return RestAssured.given().log().all()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/sessions")
            .then().log().all()
            .extract().as(SessionResponse.class).getId();
    }

    private Long 미션_등록함(MissionRequest request) {
        return RestAssured.given()
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/missions")
            .then().log().all()
            .extract().as(MissionResponse.class).getId();
    }
}
