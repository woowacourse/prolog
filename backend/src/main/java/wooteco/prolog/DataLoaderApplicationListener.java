package wooteco.prolog;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocumentRepository;
import wooteco.prolog.studylog.domain.Post;
import wooteco.prolog.studylog.domain.repository.PostRepository;

@Profile({"dev & prod"})
@AllArgsConstructor
@Configuration
public class DataLoaderApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private StudyLogDocumentRepository studyLogDocumentRepository;
    private PostRepository postRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // sync between es and db
        studyLogDocumentRepository.deleteAll();

        List<Post> posts = postRepository.findAll();
        studyLogDocumentRepository.saveAll(
            posts.stream()
                .map(Post::toStudyLogDocument)
                .collect(Collectors.toList())
        );
    }
}
