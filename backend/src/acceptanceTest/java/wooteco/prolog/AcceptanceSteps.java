package wooteco.prolog;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocumentRepository;

@Profile("acceptance")
public class AcceptanceSteps {

    @Autowired
    public AcceptanceContext context;

}
