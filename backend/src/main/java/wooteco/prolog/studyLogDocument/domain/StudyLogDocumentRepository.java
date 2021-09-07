package wooteco.prolog.studyLogDocument.domain;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

@Profile({"dev", "prod"})
public interface StudyLogDocumentRepository extends
    ElasticsearchRepository<StudyLogDocument, Long>,
    CustomStudyLogDocumentRepository<StudyLogDocument> {

    @Query("{\"query_string\": {\"fields\": [\"title\",\"content\"], \"query\": \"*?0*\"}}")
    List<StudyLogDocument> findByKeyword(String keyword, Pageable pageable);
}
