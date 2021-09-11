package wooteco.prolog.studylog.application;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.domain.StudylogDocument;

@Service
public interface DocumentService {

    void save(StudylogDocument toStudylogDocument);

    List<Long> findBySearchKeyword(String searchKeyword, Pageable pageable);

    StudylogDocument findById(Long id);

    void delete(StudylogDocument studylogDocument);

    void deleteAll();

    void sync();

    void update(StudylogDocument studylogDocument);

    SearchPage<StudylogDocument> findBySearchKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        LocalDate start,
        LocalDate end,
        Pageable pageable
    );
}
