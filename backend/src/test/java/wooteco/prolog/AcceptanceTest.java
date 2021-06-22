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
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.domain.Role;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AcceptanceTest {
    public static Member MEMBER1 = new Member(1L, "쏘로로롱", "soulG", Role.CREW, 1L, "https://avatars.githubusercontent.com/u/52682603?v=4");

    @LocalServerPort
    private int port;

    public TokenResponse 로그인_사용자;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        로그인_사용자 = given().log().all()
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
