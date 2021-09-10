package wooteco.prolog.studylog.studylog.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import wooteco.prolog.studylog.domain.SearchDocument;

public class StudyLogDocumentTestFixture {

    private static String 포함된_단어 = "테스트";
    private static String 일부만_포함된_단어 = "테스";

    private static SearchDocument searchDocument1 = new SearchDocument(1L,
                                                                       addFront("앞", 포함된_단어),
                                                                       "아예 다른 내용");
    private static SearchDocument searchDocument2 = new SearchDocument(2L,
                                                                       addLast(포함된_단어, "뒤"),
                                                                       "아예 다른 내용");
    private static SearchDocument searchDocument3 = new SearchDocument(3L,
                                                                       addBetween("앞", 포함된_단어,
                                                                                        "뒤"),
                                                                       "아예 다른 내용");
    private static SearchDocument searchDocument4 = new SearchDocument(4L, 일부만_포함된_단어,
                                                                       "아예 다른 내용");
    private static SearchDocument searchDocument5 = new SearchDocument(5L, "아예 다른 단어",
                                                                       포함된_단어);
    private static SearchDocument searchDocument6 = new SearchDocument(6L, "아예 다른 단어",
                                                                       addFront("앞", 포함된_단어));
    private static SearchDocument searchDocument7 = new SearchDocument(7L, "아예 다른 단어",
                                                                       addLast(포함된_단어, "뒤"));
    private static SearchDocument searchDocument8 = new SearchDocument(8L, "아예 다른 단어",
                                                                       addBetween("앞", 포함된_단어,
                                                                                        "뒤"));
    private static SearchDocument searchDocument9 = new SearchDocument(9L, "아예 다른 단어",
                                                                       일부만_포함된_단어);
    private static SearchDocument searchDocument10 = new SearchDocument(10L, "하자",
                                                                        "아예 다른 내용");
    private static SearchDocument searchDocument11 = new SearchDocument(11L, "아예 다른 단어",
                                                                        "하자");

    public static List<SearchDocument> searchDocuments = new ArrayList<>(
        Arrays.asList(searchDocument1, searchDocument2, searchDocument3,
                      searchDocument4, searchDocument5, searchDocument6, searchDocument7,
                      searchDocument8, searchDocument9, searchDocument10,
                      searchDocument11));

    private static String addFront(String front, String word) {
        return front + " " + word;
    }

    private static String addLast(String word, String back) {
        return word + " " + back;
    }

    private static String addBetween(String front, String word, String back) {
        return front + " " + word + " " + back;
    }
}
