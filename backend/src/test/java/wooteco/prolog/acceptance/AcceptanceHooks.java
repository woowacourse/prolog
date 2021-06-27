package wooteco.prolog.acceptance;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
public class AcceptanceHooks {
    @LocalServerPort
    private int port;

    @Before("@api")
    public void setupForApi() {
        RestAssured.port = port;
    }
}

