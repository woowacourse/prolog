package wooteco.prolog.studyLogDocument.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StudyLogDocumentRepository extends
    ElasticsearchRepository<StudyLogDocument, Long> {

    @Query("{\"query_string\": {\"fields\": [\"title\",\"content\"], \"query\": \"*?0*\"}}")
    List<StudyLogDocument> findByKeyword(String keyword, Pageable pageable);
}
