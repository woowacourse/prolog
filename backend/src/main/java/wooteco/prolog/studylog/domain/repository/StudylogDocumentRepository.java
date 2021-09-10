package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wooteco.prolog.studylog.domain.StudylogDocument;

@Profile({"dev", "prod"})
public interface StudylogDocumentRepository extends
    ElasticsearchRepository<StudylogDocument, Long> {

    @Query("{\"query_string\": {\"fields\": [\"title\",\"content\"], \"query\": \"*?0*\"}}")
    Page<StudylogDocument> findByKeyword(String keyword, Pageable pageable);
}
