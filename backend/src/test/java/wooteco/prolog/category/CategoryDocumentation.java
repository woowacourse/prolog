package wooteco.prolog.category;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import wooteco.prolog.Documentation;
import wooteco.prolog.login.GithubLoginService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class CategoryDocumentation extends Documentation {
    @MockBean
    private GithubLoginService githubLoginService;

    @Test
    void post() {
        when(githubLoginService.createToken(anyString())).thenReturn("asdf.asdf.asdf");

        RestAssured
                .given(spec).log().all()
                .filter(document("category/list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .header("Authorization", "Bearer " + 로그인_사용자.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/categories")
                .then().log().all().extract();
    }
}
