package wooteco.prolog.steps;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.elasticsearch.common.collect.List;
import org.springframework.http.HttpStatus;
import wooteco.prolog.AcceptanceSteps;
import wooteco.prolog.fixtures.CommentAcceptanceFixture;
import wooteco.prolog.fixtures.GithubResponses;
import wooteco.prolog.studylog.application.dto.CommentMemberResponse;
import wooteco.prolog.studylog.application.dto.CommentResponse;
import wooteco.prolog.studylog.application.dto.CommentsResponse;

public class CommentStepDefinitions extends AcceptanceSteps {

    @Given("{long}번 스터디로그에 대한 댓글을 작성하고")
    @When("{long}번 스터디로그에 대한 댓글을 작성하면")
    public void 스터디로그에_대한_댓글을_작성하면(Long studylogId) {
        context.invokeHttpPostWithToken("/studylogs/" + studylogId + "/comments", CommentAcceptanceFixture.COMMENT.getRequest());
    }

    @Then("댓글이 작성된다")
    public void 댓글이_작성된다() {
        int statusCode = context.response.statusCode();

        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @When("{long}번 스터디로그의 댓글을 조회하면")
    public void test(Long studylogId) {
        context.invokeHttpGetWithToken("/studylogs/" + studylogId + "/comments");
    }

    @Then("해당 스터디로그의 댓글 목록을 조회한다")
    public void test1() {
        int statusCode = context.response.statusCode();
        CommentsResponse commentsResponse = context.response.as(CommentsResponse.class);

        assertThat(statusCode).isEqualTo(HttpStatus.OK.value());
        assertThat(commentsResponse.getData())
            .usingRecursiveComparison()
            .ignoringFields("createAt").isEqualTo(List.of(
            new CommentResponse(1L, new CommentMemberResponse(1L, GithubResponses.브라운.getName(), GithubResponses.브라운.getAvatarUrl()), "스터디로그의 댓글 내용입니다.", null),
            new CommentResponse(2L, new CommentMemberResponse(2L, GithubResponses.웨지.getName(), GithubResponses.웨지.getAvatarUrl()), "스터디로그의 댓글 내용입니다.", null)
        ));
    }
}
