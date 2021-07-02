package wooteco.prolog.post.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import wooteco.prolog.acceptance.AcceptanceSteps;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostDataResponse;
import wooteco.prolog.post.application.dto.PostResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PostStepDefinitions extends AcceptanceSteps {

    @Given("포스트 여러개를 작성하고")
    public void 포스트여러개를작성하고() {
        List<PostRequest> postRequests = Arrays.asList(
                PostAcceptanceFixture.firstPost,
                PostAcceptanceFixture.secondPost
        );

        context.invokeHttpPostWithToken("/posts", postRequests);
    }

    @When("포스트를 작성하면")
    public void 포스트를작성하면() {
        List<PostRequest> postRequests = Arrays.asList(
                PostAcceptanceFixture.firstPost
        );

        context.invokeHttpPostWithToken("/posts", postRequests);
    }

    @Then("포스트가 작성된다")
    public void 포스트가작성된다() {
        int statusCode = context.response.statusCode();
        assertThat(statusCode).isEqualTo(HttpStatus.CREATED.value());
    }

    @Given("{long}개의 포스트를 작성하고")
    public void 다수의포스트를작성하면(Long totalSize) {
        List<PostRequest> postRequests = new ArrayList<>();

        for (int i = 0; i < totalSize; i++) {
            postRequests.add(PostAcceptanceFixture.firstPost);
        }

        context.invokeHttpPostWithToken("/posts", postRequests);
    }

    @When("{long}개, {long}쪽의 페이지를 조회하면")
    public void 포스트페이지를조회하면(Long pageSize, Long pageNumber) {
        String path = String.format("/posts?page=%d&size=%d", pageNumber, pageSize);
        context.invokeHttpGet(path);
    }

    @Then("{int}개의 포스트 목록을 받는다")
    public void 다수의포스트목록을받는다(int pageSize) {
        PostResponse posts = context.response.as(PostResponse.class);

        assertThat(posts.getData().size()).isEqualTo(pageSize);
    }

    @When("포스트 목록을 조회하면")
    public void 포스트목록을조회하면() {
        context.invokeHttpGet("/posts");
    }

    @Then("포스트 목록을 받는다")
    public void 포스트목록을받는다() {
        PostResponse posts = context.response.as(PostResponse.class);

        assertThat(posts.getData().size()).isEqualTo(2);
    }

    @When("{long}번째 포스트를 조회하면")
    public void 포스트를조회하면(Long postId) {
        String path = "/posts/" + postId;
        context.invokeHttpGet(path);
    }

    @Then("{long}번째 포스트가 조회된다")
    public void 포스트가조회된다(Long postId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        String path = "/posts/" + postId;
        context.invokeHttpGet(path);
        PostDataResponse post = context.response.as(PostDataResponse.class);

        assertThat(post).isNotNull();
    }

    @When("{long}번째 포스트를 수정하면")
    public void 포스트를수정하면(Long postId) {
        String path = "/posts/" + postId;
        context.invokeHttpPutWithToken(path, PostAcceptanceFixture.secondPost);
    }

    @Then("{long}번째 포스트가 수정된다")
    public void 포스트가수정된다(Long postId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.OK.value());

        String path = "/posts/" + postId;
        context.invokeHttpGet(path);
        PostDataResponse post = context.response.as(PostDataResponse.class);

        assertThat(post.getContent()).isEqualTo(PostAcceptanceFixture.secondPost.getContent());
    }

    @Then("에러 응답을 받는다")
    public void 에러가응답을받는다() {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @When("{long}번째 포스트를 삭제하면")
    public void 포스트를삭제하면(Long postId) {
        String path = "/posts/" + postId;
        context.invokeHttpDeleteWithToken(path);
    }

    @Then("{long}번째 포스트가 삭제된다")
    public void 포스트가삭제된다(Long postId) {
        assertThat(context.response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
