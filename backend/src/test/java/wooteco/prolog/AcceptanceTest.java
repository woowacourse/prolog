package wooteco.prolog;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.login.application.dto.TokenRequest;
import wooteco.prolog.login.application.dto.TokenResponse;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AcceptanceTest {
    @LocalServerPort
    private int port;

    public TokenResponse tokenResponse;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        tokenResponse = given().log().all()
                .body(new TokenRequest("나는 코다 나는 눈이다"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().body().as(TokenResponse.class);
    }

    public RequestSpecification given() {
        return RestAssured
                .given().log().all();
    }
}
