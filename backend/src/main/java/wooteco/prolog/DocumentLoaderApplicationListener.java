package wooteco.prolog;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import wooteco.prolog.studylog.application.StudylogDocumentService;

@Profile({"local", "test"})
@AllArgsConstructor
@Configuration
public class DocumentLoaderApplicationListener implements
    ApplicationListener<ContextRefreshedEvent> {

    private StudylogDocumentService studylogDocumentService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // sync between es and db
        studylogDocumentService.sync();
    }
}
