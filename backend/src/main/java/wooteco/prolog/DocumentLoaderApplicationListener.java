package wooteco.prolog;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import wooteco.prolog.studyLogDocument.domain.CustomStudyLogDocumentRepository;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@Profile({"local", "test"})
@AllArgsConstructor
@Configuration
public class DocumentLoaderApplicationListener implements
    ApplicationListener<ContextRefreshedEvent> {

    private CustomStudyLogDocumentRepository<StudyLogDocument> studyLogDocumentRepository;
    private StudylogRepository studylogRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // sync between es and db
        studyLogDocumentRepository.deleteAll();

        List<Studylog> posts = studylogRepository.findAll();
        studyLogDocumentRepository.saveAll(
            posts.stream()
                .map(Studylog::toStudyLogDocument)
                .collect(Collectors.toList())
        );
    }
}
