package wooteco.prolog.studylog.application;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_DOCUMENT_NOT_FOUND;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.studylog.domain.Studylog;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.repository.StudylogDocumentRepository;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

public abstract class AbstractStudylogDocumentService implements DocumentService {

    private static final String EMPTY = " ";

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

    @Override
    public StudylogDocument findById(Long id) {
        return studylogDocumentRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(STUDYLOG_DOCUMENT_NOT_FOUND));
    }

    @Override
    public void delete(StudylogDocument studylogDocument) {
        studylogDocumentRepository.delete(studylogDocument);
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

    protected List<String> preprocess(String searchKeyword) {
        String[] split = searchKeyword.split(EMPTY);
        List<String> results = new ArrayList<>();
        Collections.addAll(results, split);
        if (split.length == 1) {
            return results;
        }
        results.add(searchKeyword); // 기존 검색어도 리스트에 포함한다.
        return results;
    }
}
