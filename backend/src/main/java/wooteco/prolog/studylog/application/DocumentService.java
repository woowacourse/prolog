package wooteco.prolog.studylog.application;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.application.dto.ElasticHealthResponse;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.StudyLogDocument;
import wooteco.prolog.studylog.application.dto.ClusterHealthResponses;
import wooteco.prolog.studylog.application.dto.IndexHealthResponses;

public interface DocumentService {

    void save(StudyLogDocument toStudyLogDocument);

    StudyLogDocument findById(Long id);

    void delete(StudyLogDocument studylogDocument);

    void deleteAll();

    void sync();

    void update(StudyLogDocument studylogDocument);

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

    ElasticHealthResponse checkHealth();

    ClusterHealthResponses checkHealthOfCluster();

    IndexHealthResponses checkHealthOfIndex();

}
