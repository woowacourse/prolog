package wooteco.prolog.studyLogDocument.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;

public class StudyLogDocumentTestFixture {

    private static String 포함된_단어 = "테스트";
    private static String 일부만_포함된_단어 = "테스";

    private static StudyLogDocument studyLogDocument1 = new StudyLogDocument(1L,
                                                                             addFront("앞", 포함된_단어),
                                                                             "아예 다른 내용");
    private static StudyLogDocument studyLogDocument2 = new StudyLogDocument(2L,
                                                                             addLast(포함된_단어, "뒤"),
                                                                             "아예 다른 내용");
    private static StudyLogDocument studyLogDocument3 = new StudyLogDocument(3L,
                                                                             addBetween("앞", 포함된_단어,
                                                                                        "뒤"),
                                                                             "아예 다른 내용");
    private static StudyLogDocument studyLogDocument4 = new StudyLogDocument(4L, 일부만_포함된_단어,
                                                                             "아예 다른 내용");
    private static StudyLogDocument studyLogDocument5 = new StudyLogDocument(5L, "아예 다른 단어",
                                                                             포함된_단어);
    private static StudyLogDocument studyLogDocument6 = new StudyLogDocument(6L, "아예 다른 단어",
                                                                             addFront("앞", 포함된_단어));
    private static StudyLogDocument studyLogDocument7 = new StudyLogDocument(7L, "아예 다른 단어",
                                                                             addLast(포함된_단어, "뒤"));
    private static StudyLogDocument studyLogDocument8 = new StudyLogDocument(8L, "아예 다른 단어",
                                                                             addBetween("앞", 포함된_단어,
                                                                                        "뒤"));
    private static StudyLogDocument studyLogDocument9 = new StudyLogDocument(9L, "아예 다른 단어",
                                                                             일부만_포함된_단어);
    private static StudyLogDocument studyLogDocument10 = new StudyLogDocument(10L, "하자",
                                                                              "아예 다른 내용");
    private static StudyLogDocument studyLogDocument11 = new StudyLogDocument(11L, "아예 다른 단어",
                                                                              "하자");

    public static List<StudyLogDocument> studyLogDocuments = new ArrayList<>(
        Arrays.asList(studyLogDocument1, studyLogDocument2, studyLogDocument3,
                      studyLogDocument4, studyLogDocument5, studyLogDocument6, studyLogDocument7,
                      studyLogDocument8, studyLogDocument9, studyLogDocument10,
                      studyLogDocument11));

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
