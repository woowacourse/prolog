package wooteco.prolog.studyLogDocument.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocumentRepository;

@AllArgsConstructor
@Service
public class StudyLogDocumentService {

    private final StudyLogDocumentRepository studyLogDocumentRepository;

    public void save(StudyLogDocument studyLogDocument) {
        studyLogDocumentRepository.save(studyLogDocument);
    }

    public List<Long> findBySearchKeyword(String searchKeyword, Pageable pageable) {
        List<StudyLogDocument> studyLogs = studyLogDocumentRepository.findByKeyword(searchKeyword, pageable);
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
}
