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
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.support.fake.FakeDocumentRepository;

@Profile({"local", "test"})
@Repository
public class FakeStudylogDocumentRepository implements StudylogDocumentRepository,
    FakeDocumentRepository {

    private static final List<StudylogDocument> STUDYLOG_DOCUMENTS = new ArrayList<>();

    @Override
    public <S extends StudylogDocument> S save(S studylogDocument) {
        Optional<StudylogDocument> document = findById(studylogDocument.getId());
        document.ifPresent(STUDYLOG_DOCUMENTS::remove);
        STUDYLOG_DOCUMENTS.add(studylogDocument);
        return studylogDocument;
    }

    @Override
    public void delete(StudylogDocument studylogDocument) {
        STUDYLOG_DOCUMENTS.remove(studylogDocument);
    }

    @Override
    public void deleteAll(Iterable<? extends StudylogDocument> entities) {
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
    public <S extends StudylogDocument> Iterable<S> saveAll(Iterable<S> inputStudyLogDocuments) {
        List<StudylogDocument> studylogDocumentsWithId = new ArrayList<>();
        for (StudylogDocument studyLogDocument : inputStudyLogDocuments) {
            STUDYLOG_DOCUMENTS.add(studyLogDocument);
            STUDYLOG_DOCUMENTS.add(studyLogDocument);
        }
        return (Iterable<S>) studylogDocumentsWithId;
    }

    @Override
    public Optional<StudylogDocument> findById(Long id) {
        for (StudylogDocument studylogDocument : STUDYLOG_DOCUMENTS) {
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
    public Iterable<StudylogDocument> findAll() {
        return null;
    }

    @Override
    public Iterable<StudylogDocument> findAllById(Iterable<Long> longs) {
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
    public <S extends StudylogDocument> S indexWithoutRefresh(S entity) {
        return null;
    }

    @Override
    public Iterable<StudylogDocument> search(QueryBuilder query) {
        return null;
    }

    @Override
    public Page<StudylogDocument> search(QueryBuilder query, Pageable pageable) {
        return null;
    }

    @Override
    public Page<StudylogDocument> search(Query searchQuery) {
        return null;
    }

    @Override
    public Page<StudylogDocument> searchSimilar(StudylogDocument entity, String[] fields,
                                                Pageable pageable) {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public Iterable<StudylogDocument> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<StudylogDocument> findAll(Pageable pageable) {
        return null;
    }
}
