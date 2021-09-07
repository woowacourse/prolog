package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;

@AllArgsConstructor
@Service
public class StudylogDocumentService {

    private final StudylogDocumentRepository studyLogDocumentRepository;
    private final StudylogRepository studylogRepository;

    public void save(StudylogDocument studyLogDocument) {
        studyLogDocumentRepository.save(studyLogDocument);
    }

    public List<Long> findBySearchKeyword(String searchKeyword, Pageable pageable) {
        List<StudylogDocument> studyLogs = studyLogDocumentRepository.findByKeyword(searchKeyword,
                                                                                    pageable);
        return studyLogs.stream()
            .map(StudylogDocument::getId)
            .collect(toList());
    }

    public StudylogDocument findById(Long id) {
        return studyLogDocumentRepository.findById(id)
            .orElseThrow(StudylogDocumentNotFoundException::new);
    }

    public void delete(StudylogDocument studyLogDocument) {
        studyLogDocumentRepository.delete(studyLogDocument);
    }

    public void deleteAll() {
        studyLogDocumentRepository.deleteAll();
    }

    public void sync() {
        // sync between es and db
        studyLogDocumentRepository.deleteAll();

        List<Studylog> studylogs = studylogRepository.findAll();
        studyLogDocumentRepository.saveAll(
            studylogs.stream()
                .map(Studylog::toStudyLogDocument)
                .collect(Collectors.toList())
        );
    }

    public void update(StudylogDocument studylogDocument) {
        studyLogDocumentRepository.save(studylogDocument);
    }
}
