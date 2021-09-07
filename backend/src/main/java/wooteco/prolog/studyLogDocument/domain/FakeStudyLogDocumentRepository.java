package wooteco.prolog.studyLogDocument.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class FakeStudyLogDocumentRepository implements
    CustomStudyLogDocumentRepository<StudyLogDocument> {

    private long id;
    private static final List<StudyLogDocument> studylogs = new ArrayList<>();

    @Override
    public <S extends StudyLogDocument> S save(StudyLogDocument studyLogDocument) {
        studyLogDocument.setId(++id);
        studylogs.add(studyLogDocument);
        return (S) studyLogDocument;
    }

    @Override
    public List<StudyLogDocument> findByKeyword(String searchKeyword, Pageable pageable) {
        List<String> searchKeywords = preprocess(searchKeyword);
        HashSet<StudyLogDocument> results = new HashSet<>();
        for (String search : searchKeywords) {
            studylogs.stream().filter(
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
        studylogs.remove(studyLogDocument);
    }

    @Override
    public void deleteAll() {
        studylogs.clear();
        id = 0;
    }

    @Override
    public <S extends StudyLogDocument> Iterable<S> saveAll(
        List<StudyLogDocument> studyLogDocuments) {
        List<StudyLogDocument> studyLogDocumentsWithId = new ArrayList<>();
        for (StudyLogDocument studyLogDocument : studyLogDocuments) {
            studyLogDocument.setId(++id);
            studylogs.add(studyLogDocument);
            studylogs.add(studyLogDocument);
        }
        return (Iterable<S>) studyLogDocumentsWithId;
    }

    @Override
    public Iterable<StudyLogDocument> findAll() {
        return new ArrayList<>(studylogs);
    }
}
