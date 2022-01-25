package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.application.dto.StudylogDocumentResponse;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.StudylogDocumentQueryBuilder;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.infrastructure.HealthCheckClient;

@Profile({"elastic", "dev", "prod"})
@Service
public class StudylogDocumentService extends AbstractStudylogDocumentService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public StudylogDocumentService(
        StudylogDocumentRepository studylogDocumentRepository,
        StudylogRepository studylogRepository,
        HealthCheckClient healthCheckClient,
        ElasticsearchRestTemplate elasticsearchRestTemplate){
        super(studylogDocumentRepository, studylogRepository, healthCheckClient);
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public StudylogDocumentResponse findBySearchKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        LocalDate start,
        LocalDate end,
        Pageable pageable
    ) {

        final Query query = StudylogDocumentQueryBuilder.makeQuery(preprocess(keyword), tags, missions, levels,
                                                                   usernames, start, end, pageable);

        // Query 결과를 ES에서 조회한다.
        final SearchHits<StudylogDocument> searchHits
            = elasticsearchRestTemplate.search(query, StudylogDocument.class, IndexCoordinates.of("studylog-document"));

        // 조회된 SearchHits를 페이징할 수 있는 SearchPage로 변경한다.
        final SearchPage<StudylogDocument> searchPages
            = SearchHitSupport.searchPageFor(searchHits, query.getPageable());

        final List<Long> studylogIds = searchPages.stream()
            .map(searchPage -> searchPage.getContent().getId())
            .collect(toList());

        return StudylogDocumentResponse.of(studylogIds,
                                            searchPages.getTotalElements(),
                                            searchPages.getTotalPages(),
                                            searchPages.getNumber());
    }
}
