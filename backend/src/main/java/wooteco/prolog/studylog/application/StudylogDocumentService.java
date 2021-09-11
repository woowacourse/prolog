package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.StudylogDocumentQueryBuilder;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;

@AllArgsConstructor
@Service
@Slf4j
public class StudylogDocumentService {

    private final StudylogDocumentRepository studylogDocumentRepository;
    private final StudylogRepository studylogRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void save(StudylogDocument studylogDocument) {
        studylogDocumentRepository.save(studylogDocument);
    }

    public List<Long> findBySearchKeyword(String searchKeyword,
                                          Pageable pageable) {
        Page<StudylogDocument> studylogs = studylogDocumentRepository.findByKeyword(searchKeyword,
                                                                                    pageable);
        return studylogs.stream()
            .map(StudylogDocument::getId)
            .collect(toList());
    }

    public StudylogDocument findById(Long id) {
        return studylogDocumentRepository.findById(id)
            .orElseThrow(StudylogDocumentNotFoundException::new);
    }

    public void delete(StudylogDocument studylogDocument) {
        studylogDocumentRepository.delete(studylogDocument);
    }

    public void deleteAll() {
        studylogDocumentRepository.deleteAll();
    }

    public void sync() {
        // sync between es and db
        studylogDocumentRepository.deleteAll();

        List<Studylog> studylogs = studylogRepository.findAll();
        studylogDocumentRepository.saveAll(
            studylogs.stream()
                .map(Studylog::toStudylogDocument)
                .collect(Collectors.toList())
        );

        log.info("{}개의 데이터를 동기화하였습니다.", studylogs.size());
    }

    public void update(StudylogDocument studylogDocument) {
        studylogDocumentRepository.save(studylogDocument);
    }

    // TODO
    public SearchPage<StudylogDocument> findBySearchKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        LocalDate start,
        LocalDate end,
        Pageable pageable
    ) {

        final Query query = StudylogDocumentQueryBuilder.makeQuery(keyword, tags, missions, levels,
                                                                   usernames, start, end, pageable);
        final SearchHits<StudylogDocument> searchHits
            = elasticsearchRestTemplate.search(query, StudylogDocument.class, IndexCoordinates.of("studylog-document"));
        return SearchHitSupport.searchPageFor(searchHits,
                                              query.getPageable());
    }
}
