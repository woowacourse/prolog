package wooteco.prolog;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.boot.web.server.LocalServerPort;
import wooteco.support.utils.AcceptanceTest;

@CucumberContextConfiguration
@AcceptanceTest
public class AcceptanceHooks {

    @LocalServerPort
    private int port;

    @Before("@api")
    public void setupForApi() {
        RestAssured.port = port;
    }
}

