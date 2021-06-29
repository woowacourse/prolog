package wooteco.prolog.acceptance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

@Profile("test")
public class AcceptanceSteps {
    @Autowired
    public AcceptanceContext context;
}
