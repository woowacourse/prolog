package wooteco.prolog;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import wooteco.prolog.common.DataInitializer;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AcceptanceHooks {

    @LocalServerPort
    private int port;

    @Autowired
    private DataInitializer dataInitializer;

    @Before("@api")
    public void setupForApi() {
        RestAssured.port = port;
        dataInitializer.execute();
    }

    @After("@api")
    public void deleteForApi() {
        RestAssured.port = port;
        dataInitializer.execute();
    }
}

