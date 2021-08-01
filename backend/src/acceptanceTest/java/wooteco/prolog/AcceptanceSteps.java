package wooteco.prolog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

@Profile("acceptance")
public class AcceptanceSteps {
    @Autowired
    public AcceptanceContext context;
}
