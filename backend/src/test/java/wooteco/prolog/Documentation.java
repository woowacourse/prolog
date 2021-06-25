package wooteco.prolog;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import wooteco.prolog.login.dao.MemberDao;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public class Documentation extends AcceptanceTest{
    protected RequestSpecification spec;

    @MockBean
    private GithubLoginService githubLoginService;

    @MockBean
    private JwtTokenProvider tokenProvider;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberDao memberDao;

    protected TokenResponse 로그인_사용자;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        setUp();

        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();

        String code = "1234567890qazwsxedcrfvtgbyhnujmiklop";
        TokenRequest params = new TokenRequest(code);
        when(githubLoginService.createToken(any())).thenReturn(TokenResponse.of("asdf.asdf.asdf"));

        when(tokenProvider.extractSubject(any())).thenReturn("1");

        when(memberService.findById(1L)).thenReturn(MEMBER);

        memberDao.insert(MEMBER);

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
