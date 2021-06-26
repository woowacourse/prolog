package wooteco.prolog.mission.acceptance;

import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import wooteco.prolog.acceptance.AcceptanceContext;

@Profile("test")
public class MissionAcceptanceSteps {
    @Autowired
    public AcceptanceContext context;

    @Given("미션 여러개를 생성하고")
    public void 미션여러개를생성하고() {
        context.invokeHttpPost("/missions", MissionAcceptanceFixture.mission1);
        context.invokeHttpPost("/missions", MissionAcceptanceFixture.mission2);
    }
}
