package wooteco.prolog.post.documentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.web.context.WebApplicationContext;
import wooteco.prolog.documentation.Documentation;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
import wooteco.prolog.login.ui.AuthMemberPrincipalArgumentResolver;
import wooteco.prolog.login.ui.LoginInterceptor;
import wooteco.prolog.post.acceptance.PostAcceptanceFixture;
import wooteco.prolog.post.application.PostService;
import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.post.application.dto.PostResponse;
import wooteco.prolog.post.ui.PostController;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebMvcTest(PostController.class)
public class PostDocumentation extends Documentation {
    @MockBean
    private PostService postService;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
    }

    @Test
    void 포스트를_생성한다() {
        when(postService.insertPosts(any(), any())).thenReturn(Arrays.asList(new PostResponse()));

        List<PostRequest> params = Arrays.asList(PostAcceptanceFixture.firstPost);
        given()
                .header("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjI0NjgzNzE3LCJleHAiOjE2MjQ2ODczMTcsInJvbGUiOiJDUkVXIn0.YiK7iO0HCYVcU1bczbG1KldB4ZgqLETUfBsNM_XFhVM")
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/posts")
                .then().log().all()
                .apply(document("post/create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
