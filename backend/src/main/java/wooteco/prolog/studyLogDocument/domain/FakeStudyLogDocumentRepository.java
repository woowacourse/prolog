package wooteco.prolog.studyLogDocument.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
import wooteco.support.fake.FakeDocumentRepository;

@Profile({"local", "test"})
@Repository
public class FakeStudyLogDocumentRepository implements StudyLogDocumentRepository,
    FakeDocumentRepository {

    private static final List<StudyLogDocument> studyLogs = new ArrayList<>();

    @Override
    public <S extends StudyLogDocument> S save(S studyLogDocument) {
        studyLogs.add(studyLogDocument);
        return studyLogDocument;
    }

    @Override
    public List<StudyLogDocument> findByKeyword(String searchKeyword, Pageable pageable) {
        List<String> searchKeywords = preprocess(searchKeyword);
        HashSet<StudyLogDocument> results = new HashSet<>();
        for (String search : searchKeywords) {
            studyLogs.stream().filter(
                studyLogDocument ->
                    studyLogDocument.getTitle().contains(search)
                        || studyLogDocument.getContent().contains(search))
                .forEach(results::add);
        }
        return new ArrayList<>(results);
    }

    private List<String> preprocess(String searchKeyword) {
        String[] split = searchKeyword.split(" ");
        List<String> results = new ArrayList<>();
        Collections.addAll(results, split);
        results.add(searchKeyword);
        return results;
    }

    @Override
    public void delete(StudyLogDocument studyLogDocument) {
        studyLogs.remove(studyLogDocument);
    }

    @Override
    public void deleteAll(Iterable<? extends StudyLogDocument> entities) {
        List<Long> idsForRemoving = StreamSupport.stream(entities.spliterator(), false)
            .map(it -> it.getId())
            .collect(Collectors.toList());

        studyLogs.removeIf(it -> idsForRemoving.contains(it));
    }

    @Override
    public void deleteAll() {
        studyLogs.clear();
    }

    @Override
    public <S extends StudyLogDocument> Iterable<S> saveAll(Iterable<S> studyLogDocuments) {
        List<StudyLogDocument> studyLogDocumentsWithId = new ArrayList<>();
        for (StudyLogDocument studyLogDocument : studyLogDocuments) {
            studyLogs.add(studyLogDocument);
            studyLogs.add(studyLogDocument);
        }
        return (Iterable<S>) studyLogDocumentsWithId;
    }

    @Override
    public Optional<StudyLogDocument> findById(Long aLong) {
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
