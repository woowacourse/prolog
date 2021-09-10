package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.domain.SearchDocument;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.SearchDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;

@AllArgsConstructor
@Service
public class SearchDocumentService {

    private final SearchDocumentRepository searchDocumentRepository;
    private final StudylogRepository studylogRepository;

    public void save(SearchDocument searchDocument) {
        searchDocumentRepository.save(searchDocument);
    }

    public List<Long> findBySearchKeyword(String searchKeyword,
                                          Pageable pageable) {
        Page<SearchDocument> studylogs = searchDocumentRepository.findByKeyword(searchKeyword,
                                                                                pageable);
        return studylogs.stream()
            .map(SearchDocument::getId)
            .collect(toList());
    }

    public SearchDocument findById(Long id) {
        return searchDocumentRepository.findById(id)
            .orElseThrow(StudylogDocumentNotFoundException::new);
    }

    public void delete(SearchDocument searchDocument) {
        searchDocumentRepository.delete(searchDocument);
    }

    public void deleteAll() {
        searchDocumentRepository.deleteAll();
    }

    public void sync() {
        // sync between es and db
        searchDocumentRepository.deleteAll();

        List<Studylog> studylogs = studylogRepository.findAll();
        searchDocumentRepository.saveAll(
            studylogs.stream()
                .map(Studylog::toStudylogDocument)
                .collect(Collectors.toList())
        );
    }

    public void update(SearchDocument searchDocument) {
        searchDocumentRepository.save(searchDocument);
    }

    public List<Long> findByKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        Pageable pageable
    ) {

        Page<SearchDocument> searchDocuments = searchDocumentRepository
            .findByMultipleConditions(
                keyword,
                tags,
                missions,
                levels,
                usernames,
                pageable
            );

        return searchDocuments.stream()
            .map(SearchDocument::getId)
            .collect(toList());
    }
}
