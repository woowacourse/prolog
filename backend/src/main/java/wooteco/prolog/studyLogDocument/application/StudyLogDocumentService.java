package wooteco.prolog.studyLogDocument.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.prolog.studyLogDocument.domain.CustomStudyLogDocumentRepository;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

@AllArgsConstructor
@Service
public class StudyLogDocumentService {

    private final CustomStudyLogDocumentRepository<StudyLogDocument> studyLogDocumentRepository;
//    private final StudyLogDocumentRepositoryCustom studyLogDocumentRepository;
    private final StudylogRepository studylogRepository;

    public void save(StudyLogDocument studyLogDocument) {
        studyLogDocumentRepository.save(studyLogDocument);
    }

    public List<Long> findBySearchKeyword(String searchKeyword, Pageable pageable) {
        List<StudyLogDocument> studyLogs = studyLogDocumentRepository.findByKeyword(searchKeyword,
            pageable);
        return studyLogs.stream()
            .map(StudyLogDocument::getId)
            .collect(toList());
    }

    public void delete(StudyLogDocument studyLogDocument) {
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

    public List<StudyLogDocument> findAll() {
        return (List<StudyLogDocument>) studyLogDocumentRepository.findAll();
    }
}
