package wooteco.prolog.studylog.application;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.infrastructure.dto.ClusterHealthDto;
import wooteco.prolog.studylog.infrastructure.dto.IndexHealthDto;

public interface DocumentService {

    void save(StudylogDocument toStudylogDocument);

    StudylogDocument findById(Long id);

    void delete(StudylogDocument studylogDocument);

    void deleteAll();

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

    List<ClusterHealthDto> checkHealthOfCluster();

    List<IndexHealthDto> checkHealthOfIndex();
}
