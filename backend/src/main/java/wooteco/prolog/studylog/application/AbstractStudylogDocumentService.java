package wooteco.prolog.studylog.application;

import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.domain.repository.StudylogRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_DOCUMENT_NOT_FOUND;

public abstract class AbstractStudylogDocumentService implements DocumentService {

    private static final String EMPTY = " ";

    protected final StudylogRepository studylogRepository;

    public AbstractStudylogDocumentService(StudylogRepository studylogRepository) {
        this.studylogRepository = studylogRepository;
    }

    @Override
    public void save(StudylogDocument studylogDocument) {
        // TODO: ES와 같이 제거됨. 다시 구현 필요
    }

    @Override
    public StudylogDocument findById(Long id) {
        // TODO: ES와 같이 제거됨. 다시 구현 필요

        throw new BadRequestException(STUDYLOG_DOCUMENT_NOT_FOUND);
    }

    @Override
    public void delete(StudylogDocument studylogDocument) {
        // TODO: ES와 같이 제거됨. 다시 구현 필요
    }

    @Override
    public void sync() {
        // TODO: ES와 같이 제거됨. 다시 구현 필요
    }

    @Override
    public void update(StudylogDocument studylogDocument) {
        // TODO: ES와 같이 제거됨. 다시 구현 필요
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
