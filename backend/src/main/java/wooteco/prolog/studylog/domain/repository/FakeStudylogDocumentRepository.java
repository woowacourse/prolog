package wooteco.prolog.studylog.domain.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

    private static final List<StudylogDocument> studylogDocuments = new ArrayList<>();

    @Override
    public <S extends StudylogDocument> S save(S studylogDocument) {
        Optional<StudylogDocument> document = findById(studylogDocument.getId());
        document.ifPresent(studylogDocuments::remove);
        studylogDocuments.add(studylogDocument);
        return studylogDocument;
    }

    @Override
    public List<StudylogDocument> findByKeyword(String searchKeyword, Pageable pageable) {
        List<String> searchKeywords = preprocess(searchKeyword);
        HashSet<StudylogDocument> results = new HashSet<>();
        for (String search : searchKeywords) {
            studylogDocuments.stream().filter(
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
    public void delete(StudylogDocument studylogDocument) {
        studylogDocuments.remove(studylogDocument);
    }

    @Override
    public void deleteAll(Iterable<? extends StudylogDocument> entities) {
        List<Long> idsForRemoving = StreamSupport.stream(entities.spliterator(), false)
            .map(it -> it.getId())
            .collect(Collectors.toList());

        studylogDocuments.removeIf(it -> idsForRemoving.contains(it));
    }

    @Override
    public void deleteAll() {
        studylogDocuments.clear();
    }

    @Override
    public <S extends StudylogDocument> Iterable<S> saveAll(Iterable<S> inputStudyLogDocuments) {
        List<StudylogDocument> studylogDocumentsWithId = new ArrayList<>();
        for (StudylogDocument studyLogDocument : inputStudyLogDocuments) {
            studylogDocuments.add(studyLogDocument);
            studylogDocuments.add(studyLogDocument);
        }
        return (Iterable<S>) studylogDocumentsWithId;
    }

    @Override
    public Optional<StudylogDocument> findById(Long id) {
        for (StudylogDocument studylogDocument : studylogDocuments) {
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
