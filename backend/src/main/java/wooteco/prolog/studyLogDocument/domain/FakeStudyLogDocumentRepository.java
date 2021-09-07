package wooteco.prolog.studyLogDocument.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Profile({"local", "test"})
@Repository
public class FakeStudyLogDocumentRepository implements
    CustomStudyLogDocumentRepository<StudyLogDocument> {

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
    public void deleteAll() {
        studyLogs.clear();
    }

    @Override
    public <S extends StudyLogDocument> Iterable<S> saveAll(
        Iterable<S> studyLogDocuments) {
        List<StudyLogDocument> studyLogDocumentsWithId = new ArrayList<>();
        for (StudyLogDocument studyLogDocument : studyLogDocuments) {
            studyLogs.add(studyLogDocument);
            studyLogs.add(studyLogDocument);
        }
        return (Iterable<S>) studyLogDocumentsWithId;
    }
}
