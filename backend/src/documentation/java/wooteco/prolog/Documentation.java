package wooteco.prolog;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import wooteco.prolog.common.DataInitializer;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class Documentation {

    public TokenResponse 로그인_사용자;
    protected RequestSpecification spec;
    @LocalServerPort
    protected int port;
    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;
        dataInitializer.execute();

        로그인_사용자 = RestAssured.given().log().all()
            .body(new TokenRequest(GithubResponses.소롱.getCode()))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when().post("/login/token")
            .then().log().all()
            .extract().body().as(TokenResponse.class);

        HashMap<String, Object> data = new HashMap<>();
        data.put("role", "CREW");

        final RequestSpecification requestSpecification = RestAssured
            .given().log().all()
            .body(data).contentType(ContentType.JSON)
            .auth().oauth2(로그인_사용자.getAccessToken());
        requestSpecification.patch("/members/" + 1 + "/role");

        this.spec = new RequestSpecBuilder()
            .addFilter(documentationConfiguration(restDocumentation))
            .build();
    }

    public RequestSpecification given(String identifier) {
        return RestAssured
            .given(spec).log().all()
            .filter(document(identifier,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())));
    }
}
