package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;

@AllArgsConstructor
@Service
public class StudylogDocumentService {

    private final StudylogDocumentRepository studylogDocumentRepository;
    private final StudylogRepository studylogRepository;

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
    }

    public void update(StudylogDocument studylogDocument) {
        studylogDocumentRepository.save(studylogDocument);
    }

    public List<Long> findByKeyword(
        String keyword,
        List<Long> tags,
        List<Long> missions,
        List<Long> levels,
        List<String> usernames,
        Pageable pageable
    ) {

        Page<StudylogDocument> studylogDocuments = studylogDocumentRepository
            .findByMultipleConditions(
                keyword,
                tags,
                missions,
                levels,
                usernames,
                pageable
            );

        return studylogDocuments.stream()
            .map(StudylogDocument::getId)
            .collect(toList());
    }
}
