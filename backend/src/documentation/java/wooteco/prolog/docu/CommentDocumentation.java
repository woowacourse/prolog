package wooteco.prolog.docu;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static wooteco.prolog.ResponseFixture.COMMENT;
import static wooteco.prolog.ResponseFixture.COMMENTS_RESPONSE;

import io.restassured.module.mockmvc.response.ValidatableMockMvcResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.NewDocumentation;
import wooteco.prolog.studylog.application.CommentService;
import wooteco.prolog.studylog.application.dto.CommentChangeRequest;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;
import wooteco.prolog.studylog.ui.CommentController;

@WebMvcTest(controllers = CommentController.class)
public class CommentDocumentation extends NewDocumentation {

    @MockBean
    private CommentService commentService;

    @Test
    void 댓글을_등록한다() {
        //given, when
        CommentCreateRequest params = new CommentCreateRequest(COMMENT);
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(params)
                .when().post("/studylogs/{studylogId}/comments", 1L)
                .then().log().all();

        //then
        response.expect(status().isCreated());
        response.expect(header().exists("Location"));

        //docs
        response.apply(document("comment/create"));
    }

    @Test
    void 단일_스터디로그에_대한_댓글을_조회한다() {
        //given
        given(commentService.findComments(any()))
                .willReturn(COMMENTS_RESPONSE);

        //when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/studylogs/{studylogId}/comments", 1L)
                .then().log().all();

        //then
        response.expect(status().isOk());

        //docs
        response.apply(document("comment/showAll"));
    }

    @Test
    void 댓글을_수정한다() {
        //given, when
        CommentChangeRequest params = new CommentChangeRequest("수정된 댓글의 내용입니다.");
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(params)
                .when().put("/studylogs/{studylogId}/comments/{commentId}", 1L, 1L)
                .then().log().all();

        //then
        response.expect(status().isNoContent());

        //docs
        response.apply(document("comment/update"));
    }

    @Test
    void 댓글을_삭제한다() {
        //given, when
        ValidatableMockMvcResponse response = given
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .when().delete("/studylogs/{studylogId}/comments/{commentId}", 1L, 1L)
                .then().log().all();

        //then
        response.expect(status().isNoContent());

        //docs
        response.apply(document("comment/delete"));
    }
}
