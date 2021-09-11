package wooteco.prolog.studylog.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;

public abstract class AbstractStudylogDocumentService implements DocumentService {

    protected final StudylogDocumentRepository studylogDocumentRepository;
    protected final StudylogRepository studylogRepository;

    public AbstractStudylogDocumentService(StudylogDocumentRepository studylogDocumentRepository,
                                           StudylogRepository studylogRepository) {
        this.studylogDocumentRepository = studylogDocumentRepository;
        this.studylogRepository = studylogRepository;
    }

    @Override
    public void save(StudylogDocument studylogDocument) {
        studylogDocumentRepository.save(studylogDocument);
    }

    @Deprecated
    @Override
    public List<Long> findBySearchKeyword(String searchKeyword,
                                          Pageable pageable) {
        Page<StudylogDocument> studylogs = studylogDocumentRepository.findByKeyword(searchKeyword,
                                                                                    pageable);
        return studylogs.stream()
            .map(StudylogDocument::getId)
            .collect(toList());
    }

    @Override
    public StudylogDocument findById(Long id) {
        return studylogDocumentRepository.findById(id)
            .orElseThrow(StudylogDocumentNotFoundException::new);
    }

    @Override
    public void delete(StudylogDocument studylogDocument) {
        studylogDocumentRepository.delete(studylogDocument);
    }

    @Override
    public void deleteAll() {
        studylogDocumentRepository.deleteAll();
    }

    @Override
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

    @Override
    public void update(StudylogDocument studylogDocument) {
        studylogDocumentRepository.save(studylogDocument);
    }
}
