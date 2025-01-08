package wooteco.prolog.studylog.application;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.StudylogDocument;

public interface DocumentService {

    void save(StudylogDocument toStudylogDocument);

    StudylogDocument findById(Long id);

    void delete(StudylogDocument studylogDocument);

    void sync();

    void update(StudylogDocument studylogDocument);

    StudylogDocumentResponse findBySearchKeyword(
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
