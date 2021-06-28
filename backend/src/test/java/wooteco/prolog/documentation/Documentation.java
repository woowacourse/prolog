package wooteco.prolog.documentation;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;
import wooteco.prolog.login.ui.AuthMemberPrincipalArgumentResolver;
import wooteco.prolog.login.ui.LoginInterceptor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("docu")
public class Documentation {
    @MockBean
    private AuthMemberPrincipalArgumentResolver argumentResolver;
    @MockBean
    private LoginInterceptor loginInterceptor;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        RestAssuredMockMvc.webAppContextSetup(context, documentationConfiguration(restDocumentation));
    }

    protected static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()
                        .removePort(),
                prettyPrint());
    }

    protected static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());
    }

    public MockMvcRequestSpecification given() {
        return RestAssuredMockMvc
                .given().log().all();
    }
}