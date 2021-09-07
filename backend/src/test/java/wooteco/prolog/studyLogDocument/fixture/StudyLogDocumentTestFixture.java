package wooteco.prolog.studyLogDocument.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import wooteco.prolog.studylog.domain.StudylogDocument;

public class StudyLogDocumentTestFixture {

    private static String 포함된_단어 = "테스트";
    private static String 일부만_포함된_단어 = "테스";

    private static StudylogDocument studylogDocument1 = new StudylogDocument(1L,
                                                                             addFront("앞", 포함된_단어),
                                                                             "아예 다른 내용");
    private static StudylogDocument studylogDocument2 = new StudylogDocument(2L,
                                                                             addLast(포함된_단어, "뒤"),
                                                                             "아예 다른 내용");
    private static StudylogDocument studylogDocument3 = new StudylogDocument(3L,
                                                                             addBetween("앞", 포함된_단어,
                                                                                        "뒤"),
                                                                             "아예 다른 내용");
    private static StudylogDocument studylogDocument4 = new StudylogDocument(4L, 일부만_포함된_단어,
                                                                             "아예 다른 내용");
    private static StudylogDocument studylogDocument5 = new StudylogDocument(5L, "아예 다른 단어",
                                                                             포함된_단어);
    private static StudylogDocument studylogDocument6 = new StudylogDocument(6L, "아예 다른 단어",
                                                                             addFront("앞", 포함된_단어));
    private static StudylogDocument studylogDocument7 = new StudylogDocument(7L, "아예 다른 단어",
                                                                             addLast(포함된_단어, "뒤"));
    private static StudylogDocument studylogDocument8 = new StudylogDocument(8L, "아예 다른 단어",
                                                                             addBetween("앞", 포함된_단어,
                                                                                        "뒤"));
    private static StudylogDocument studylogDocument9 = new StudylogDocument(9L, "아예 다른 단어",
                                                                             일부만_포함된_단어);
    private static StudylogDocument studylogDocument10 = new StudylogDocument(10L, "하자",
                                                                              "아예 다른 내용");
    private static StudylogDocument studylogDocument11 = new StudylogDocument(11L, "아예 다른 단어",
                                                                              "하자");

    public static List<StudylogDocument> studylogDocuments = new ArrayList<>(
        Arrays.asList(studylogDocument1, studylogDocument2, studylogDocument3,
                      studylogDocument4, studylogDocument5, studylogDocument6, studylogDocument7,
                      studylogDocument8, studylogDocument9, studylogDocument10,
                      studylogDocument11));

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
