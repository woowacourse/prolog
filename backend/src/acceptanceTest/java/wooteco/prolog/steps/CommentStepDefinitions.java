package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.fixtures.CommentAcceptanceFixture.COMMENT;
import static wooteco.prolog.fixtures.CommentAcceptanceFixture.UPDATED_COMMENT;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentsResponse;

public class CommentStepDefinitions extends AcceptanceSteps {

    @Given("{long}번 스터디로그에 대한 댓글을 작성하고")
    @When("{long}번 스터디로그에 대한 댓글을 작성하면")
    public void 스터디로그에_대한_댓글을_작성하면(Long studylogId) {
        context.invokeHttpPostWithToken("/studylogs/" + studylogId + "/comments",
            COMMENT.getCreateRequest());
    }

    @Then("댓글이 작성된다")
    public void 댓글이_작성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("{long}번 스터디로그의 댓글을 조회하면")
    public void 스터디로그의_댓글을_조회하면(Long studylogId) {
        context.invokeHttpGetWithToken("/studylogs/" + studylogId + "/comments");
    }

    @Then("해당 스터디로그의 댓글 목록을 조회한다")
    public void 해당_스터디로그의_댓글_목록을_조회한다() {
        int statusCode = context.response.statusCode();
        CommentsResponse commentsResponse = context.response.as(CommentsResponse.class);

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
        assertThat(commentsResponse.getData())
            .usingRecursiveComparison()
            .ignoringFields("createAt").isEqualTo(List.of(
                new CommentResponse(1L, new CommentMemberResponse(1L, GithubResponses.브라운.getLogin(),
                    GithubResponses.브라운.getName(), GithubResponses.브라운.getAvatarUrl(), "CREW"),
                    "스터디로그의 댓글 내용입니다.", null),
                new CommentResponse(2L, new CommentMemberResponse(2L, GithubResponses.웨지.getLogin(),
                    GithubResponses.웨지.getName(), GithubResponses.웨지.getAvatarUrl(), "CREW"),
                    "스터디로그의 댓글 내용입니다.", null)
            ));
    }

    @When("{long}번 스터디로그에 대한 {long}번 댓글을 수정하면")
    public void 스터디로그에_대한_댓글을_수정하면(Long studylogId, Long commentId) {
        context.invokeHttpPutWithToken("/studylogs/" + studylogId + "/comments/" + commentId,
            UPDATED_COMMENT.getUpdateRequest());
    }

    @When("{long}번 스터디로그에 대한 {long}번 댓글을 삭제하면")
    public void 스터디로그에_대한_댓글을_삭제하면(Long studylogId, Long commentId) {
        context.invokeHttpDeleteWithToken("/studylogs/" + studylogId + "/comments/" + commentId);
    }

    @Then("댓글이 수정된다")
    public void 댓글이_수정_된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Then("댓글이 삭제된다")
    public void 댓글이_삭제_된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
