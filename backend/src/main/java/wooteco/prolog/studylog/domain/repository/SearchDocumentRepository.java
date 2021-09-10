package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wooteco.prolog.studylog.domain.SearchDocument;

@Profile({"dev", "prod"})
public interface SearchDocumentRepository extends
    ElasticsearchRepository<SearchDocument, Long> {

    @Query("{\"query_string\": {\"fields\": [\"title\",\"content\"], \"query\": \"*?0*\"}}")
    Page<SearchDocument> findByKeyword(String keyword, Pageable pageable);

    @Query
    Page<SearchDocument> findByMultipleConditions(String keyword, List<Long> tags,
                                                  List<Long> missions, List<Long> levels,
                                                  List<String> usernames, Pageable pageable);
}
