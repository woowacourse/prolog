package wooteco.prolog.studyLogDocument.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomStudyLogDocumentRepository<T> {

    <S extends T> S save(StudyLogDocument studyLogDocument);

    List<T> findByKeyword(String searchKeyword, Pageable pageable);

    void delete(StudyLogDocument studyLogDocument);

    void deleteAll();

    <S extends StudyLogDocument> Iterable<S> saveAll(List<StudyLogDocument> collect);

    Iterable<StudyLogDocument> findAll();
}
