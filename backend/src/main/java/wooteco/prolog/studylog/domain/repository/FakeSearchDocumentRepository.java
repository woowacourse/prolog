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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;
import wooteco.prolog.studylog.domain.SearchDocument;
import wooteco.support.fake.FakeDocumentRepository;

@Profile({"local", "test"})
@Repository
public class FakeSearchDocumentRepository implements SearchDocumentRepository,
    FakeDocumentRepository {

    private static final List<SearchDocument> SEARCH_DOCUMENTS = new ArrayList<>();

    @Override
    public <S extends SearchDocument> S save(S studylogDocument) {
        Optional<SearchDocument> document = findById(studylogDocument.getId());
        document.ifPresent(SEARCH_DOCUMENTS::remove);
        SEARCH_DOCUMENTS.add(studylogDocument);
        return studylogDocument;
    }

    @Override
    public Page<SearchDocument> findByKeyword(String searchKeyword, Pageable pageable) {
        List<String> searchKeywords = preprocess(searchKeyword);
        HashSet<SearchDocument> results = new HashSet<>();
        for (String search : searchKeywords) {
            SEARCH_DOCUMENTS.stream().filter(
                    studyLogDocument ->
                        studyLogDocument.getTitle().contains(search)
                            || studyLogDocument.getContent().contains(search))
                .forEach(results::add);
        }
        return new PageImpl<>(new ArrayList<>(results), pageable, 20);
    }

    private List<String> preprocess(String searchKeyword) {
        String[] split = searchKeyword.split(" ");
        List<String> results = new ArrayList<>();
        Collections.addAll(results, split);
        results.add(searchKeyword);
        return results;
    }

    @Override
    public void delete(SearchDocument searchDocument) {
        SEARCH_DOCUMENTS.remove(searchDocument);
    }

    @Override
    public void deleteAll(Iterable<? extends SearchDocument> entities) {
        List<Long> idsForRemoving = StreamSupport.stream(entities.spliterator(), false)
            .map(it -> it.getId())
            .collect(Collectors.toList());

        SEARCH_DOCUMENTS.removeIf(it -> idsForRemoving.contains(it));
    }

    @Override
    public void deleteAll() {
        SEARCH_DOCUMENTS.clear();
    }

    @Override
    public <S extends SearchDocument> Iterable<S> saveAll(Iterable<S> inputStudyLogDocuments) {
        List<SearchDocument> searchDocumentsWithId = new ArrayList<>();
        for (SearchDocument studyLogDocument : inputStudyLogDocuments) {
            SEARCH_DOCUMENTS.add(studyLogDocument);
            SEARCH_DOCUMENTS.add(studyLogDocument);
        }
        return (Iterable<S>) searchDocumentsWithId;
    }

    @Override
    public Optional<SearchDocument> findById(Long id) {
        for (SearchDocument searchDocument : SEARCH_DOCUMENTS) {
            if (Objects.equals(searchDocument.getId(), id)) {
                return Optional.of(searchDocument);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<SearchDocument> findAll() {
        return null;
    }

    @Override
    public Iterable<SearchDocument> findAllById(Iterable<Long> longs) {
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
    public <S extends SearchDocument> S indexWithoutRefresh(S entity) {
        return null;
    }

    @Override
    public Iterable<SearchDocument> search(QueryBuilder query) {
        return null;
    }

    @Override
    public Page<SearchDocument> search(QueryBuilder query, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SearchDocument> search(Query searchQuery) {
        return null;
    }

    @Override
    public Page<SearchDocument> searchSimilar(SearchDocument entity, String[] fields,
                                              Pageable pageable) {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public Iterable<SearchDocument> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SearchDocument> findAll(Pageable pageable) {
        return null;
    }
}
