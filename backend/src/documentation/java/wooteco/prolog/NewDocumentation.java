package wooteco.prolog;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("docu")
@ExtendWith({RestDocumentationExtension.class})
public class NewDocumentation {

    public MockMvcRequestSpecification given;

    public String accessToken;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        given = RestAssuredMockMvc.given()
            .mockMvc(MockMvcBuilders.webAppContextSetup(webApplicationContext)
                         .apply(
                             documentationConfiguration(restDocumentation).operationPreprocessors()
                                 .withRequestDefaults(prettyPrint())
                                 .withResponseDefaults(prettyPrint()))
                         .build()).log().all();

        accessToken = "accessToken";
    }

}
