package wooteco.prolog.docu;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.GithubResponses;
import wooteco.prolog.comment.application.dto.CommentMemberResponse;
import wooteco.prolog.comment.application.dto.CommentResponse;
import wooteco.prolog.comment.application.dto.CommentsResponse;
import wooteco.prolog.comment.ui.dto.CommentLevellogChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentLevellogCreateRequest;
import wooteco.prolog.levellogs.application.dto.LevelLogRequest;
import wooteco.prolog.levellogs.application.dto.SelfDiscussionRequest;

public class CommentLevellogDocumentation extends Documentation {

    @Test
    void 레벨로그_댓글을_등록한다() {
        // given
        Long levellogId = 레벨로그_등록함(createLevellogRequest());

        // when
        ExtractableResponse<Response> extract = given("comment-levellog/create")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(createCommentRequest())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/levellogs/" + levellogId + "/comments")
            .then().log().all().extract();

        // then
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(extract.header("Location")).isNotNull();
    }

    @Test
    void 단일_레벨로그에_대한_댓글들을_조회한다() {
        // given
        Long levellogId = 레벨로그_등록함(createLevellogRequest());
        댓글_등록_성공되어_있음(levellogId, createCommentRequest());

        // when
        ExtractableResponse<Response> extract = given("comment-levellog/showAll")
            .when().get("/levellogs/" + levellogId + "/comments")
            .then().log().all().extract();

        // then
        CommentsResponse commentsResponse = extract.as(CommentsResponse.class);
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(commentsResponse.getData())
            .usingRecursiveComparison()
            .ignoringFields("createdAt").isEqualTo(ImmutableList.of(
                new CommentResponse(1L,
                    new CommentMemberResponse(1L, GithubResponses.소롱.getLogin(),
                        GithubResponses.소롱.getName(),
                        GithubResponses.소롱.getAvatarUrl(), "CREW"), "댓글의 내용입니다.", null)
            ));
    }

    @Test
    void 레벨로그에_대한_댓글을_수정한다() {
        // given
        Long levellogId = 레벨로그_등록함(createLevellogRequest());
        Long levellogCommentId = 댓글_등록_성공되어_있음(levellogId, createCommentRequest());

        // when
        ExtractableResponse<Response> extract = given("comment-levellog/update")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(updateCommentRequest())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().put("/levellogs/" + levellogId + "/comments/" + levellogCommentId)
            .then().log().all().extract();

        //then
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> findExtract = 댓글_조회함(levellogId);
        CommentsResponse commentsResponse = findExtract.as(CommentsResponse.class);
        CommentResponse commentResponse = commentsResponse.getData().get(0);

        assertThat(commentResponse.getContent()).isEqualTo(updateCommentRequest().getContent());
    }

    @Test
    void 레벨로그에_대한_댓글을_삭제한다() {
        // given
        Long levellogId = 레벨로그_등록함(createLevellogRequest());
        Long levellogCommentId = 댓글_등록_성공되어_있음(levellogId, createCommentRequest());

        // when
        ExtractableResponse<Response> extract = given("comment-levellog/delete")
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .when().delete("/levellogs/" + levellogId + "/comments/" + levellogCommentId)
            .then().log().all().extract();

        //then
        assertThat(extract.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }


    /**
     * Request DTO Fixture Method
     */
    private LevelLogRequest createLevellogRequest() {
        String title = "스터디로그 제목";
        String content = "스터디로그에 본문 내용임.\n" + "여기에 글을 작성할 수 있음\n";
        List<SelfDiscussionRequest> selfDiscussions = ImmutableList.of(
            new SelfDiscussionRequest("질문", "답변"));

        return new LevelLogRequest(title, content, selfDiscussions);
    }

    private CommentLevellogCreateRequest createCommentRequest() {
        return new CommentLevellogCreateRequest("댓글의 내용입니다.");
    }

    private CommentLevellogChangeRequest updateCommentRequest() {
        return new CommentLevellogChangeRequest("수정된 댓글의 내용입니다.");
    }

    private Long 댓글_등록_성공되어_있음(Long levellogId, CommentLevellogCreateRequest request) {
        ExtractableResponse<Response> response = 댓글_등록함(levellogId, request);

        String commentId = response.header("Location").split("/comments/")[1];
        assertThat(commentId).isNotNull();
        return Long.parseLong(commentId);
    }

    private ExtractableResponse<Response> 댓글_등록함(Long levellogId,
                                                 CommentLevellogCreateRequest request) {
        return RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/levellogs/" + levellogId + "/comments")
            .then().log().all().extract();
    }

    private Long 레벨로그_등록함(LevelLogRequest request) {
        ExtractableResponse<Response> extract = RestAssured.given().log().all()
            .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
            .body(request)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().log().all()
            .post("/levellogs")
            .then().log().all().extract();

        return Long.parseLong(extract.header("Location").split("/levellogs/")[1]);
    }

    private ExtractableResponse<Response> 댓글_조회함(Long levellogId) {
        return RestAssured.given().log().all()
            .when().get("/levellogs/" + levellogId + "/comments")
            .then().log().all().extract();
    }
}
