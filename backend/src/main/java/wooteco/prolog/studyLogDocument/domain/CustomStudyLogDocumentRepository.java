package wooteco.prolog.studyLogDocument.domain;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomStudyLogDocumentRepository<T> {

    <S extends T> S save(S studyLogDocument);

    List<T> findByKeyword(String searchKeyword, Pageable pageable);

    void delete(T studyLogDocument);

    void deleteAll();

    <S extends T> Iterable<S> saveAll(Iterable<S> collect);
}
