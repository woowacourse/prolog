package wooteco.prolog.studyLogDocument.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FakeStudyLogDocumentRepository<T> implements CustomStudyLogDocumentRepository<StudyLogDocument> {

    @Override
    public <S extends StudyLogDocument> S save(StudyLogDocument studyLogDocument) {
        return null;
    }

    @Override
    public List<StudyLogDocument> findByKeyword(String searchKeyword, Pageable pageable) {
        return null;
    }

    @Override
    public void delete(StudyLogDocument studyLogDocument) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends StudyLogDocument> Iterable<S> saveAll(List<StudyLogDocument> collect) {
        return null;
    }
}
