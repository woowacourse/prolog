package wooteco.prolog;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.application.MemberService;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(RestDocumentationExtension.class)
public class Documentation {
    public static Member MEMBER1 = new Member(1L, "쏘로로롱", Role.CREW, 1L, "https://avatars.githubusercontent.com/u/52682603?v=4");

    @LocalServerPort
    int port;

    protected RequestSpecification spec;

    @MockBean
    private GithubLoginService githubLoginService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private MemberService memberService;

    protected TokenResponse 로그인_사용자;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();

        String code = "1234567890qazwsxedcrfvtgbyhnujmiklop";
        TokenRequest params = new TokenRequest(code);
        when(githubLoginService.createToken(any())).thenReturn(TokenResponse.of("asdf.asdf.asdf"));

        when(tokenProvider.extractSubject(any())).thenReturn("1");

        when(memberService.findById(1L)).thenReturn(MEMBER1);

        로그인_사용자 = RestAssured
                .given(spec).log().all()
                .filter(document("login/token",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .body(params)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().as(TokenResponse.class);
    }

    public RequestSpecification given(String identifier) {
        return RestAssured
                .given(spec).log().all()
                .filter(document(identifier,
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
