package wooteco.prolog.studylog.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import wooteco.prolog.studylog.domain.StudyLogDocument;
import wooteco.support.fake.FakeDocumentRepository;

@Profile({"local", "test"})
@Repository
public class FakeStudyLogDocumentRepository implements StudyLogDocumentRepository,
    FakeDocumentRepository {

    private static final List<StudyLogDocument> STUDYLOG_DOCUMENTS = new ArrayList<>();

    @Override
    public <S extends StudyLogDocument> S save(S studylogDocument) {
        Optional<StudyLogDocument> document = findById(studylogDocument.getId());
        document.ifPresent(STUDYLOG_DOCUMENTS::remove);
        STUDYLOG_DOCUMENTS.add(studylogDocument);
        return studylogDocument;
    }

    @Override
    public void delete(StudyLogDocument studylogDocument) {
        STUDYLOG_DOCUMENTS.remove(studylogDocument);
    }

    @Override
    public void deleteAll(Iterable<? extends StudyLogDocument> entities) {
        List<Long> idsForRemoving = StreamSupport.stream(entities.spliterator(), false)
            .map(it -> it.getId())
            .collect(Collectors.toList());

        STUDYLOG_DOCUMENTS.removeIf(it -> idsForRemoving.contains(it));
    }

    @Override
    public void deleteAll() {
        STUDYLOG_DOCUMENTS.clear();
    }

    @Override
    public <S extends StudyLogDocument> Iterable<S> saveAll(Iterable<S> inputStudylogDocuments) {
        List<StudyLogDocument> studyLogDocumentsWithId = new ArrayList<>();
        for (StudyLogDocument studylogDocument : inputStudylogDocuments) {
            STUDYLOG_DOCUMENTS.add(studylogDocument);
            STUDYLOG_DOCUMENTS.add(studylogDocument);
        }
        return (Iterable<S>) studyLogDocumentsWithId;
    }

    @Override
    public Optional<StudyLogDocument> findById(Long id) {
        for (StudyLogDocument studylogDocument : STUDYLOG_DOCUMENTS) {
            if (Objects.equals(studylogDocument.getId(), id)) {
                return Optional.of(studylogDocument);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<StudyLogDocument> findAll() {
        return null;
    }

    @Override
    public Iterable<StudyLogDocument> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public <S extends StudyLogDocument> S indexWithoutRefresh(S entity) {
        return null;
    }

    @Override
    public Iterable<StudyLogDocument> search(QueryBuilder query) {
        return null;
    }

    @Override
    public Page<StudyLogDocument> search(QueryBuilder query, Pageable pageable) {
        return null;
    }

    @Override
    public Page<StudyLogDocument> search(Query searchQuery) {
        return null;
    }

    @Override
    public Page<StudyLogDocument> searchSimilar(StudyLogDocument entity, String[] fields,
                                                Pageable pageable) {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public Iterable<StudyLogDocument> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<StudyLogDocument> findAll(Pageable pageable) {
        return null;
    }
}
