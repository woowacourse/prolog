package wooteco.prolog.studylog.studylog.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import wooteco.prolog.studylog.domain.StudylogDocument;

public class StudyLogDocumentTestFixture {

    private static String 포함된_단어 = "테스트";
    private static String 일부만_포함된_단어 = "테스";

    private static final Long TAG_ID_1 = 1L;
    private static final Long TAG_ID_2 = 1L;
    private static final Long TAG_ID_3 = 1L;
    private static final List<Long> TAGS = Arrays.asList(TAG_ID_1, TAG_ID_2, TAG_ID_3);

    private static final Long MISSION1 = 1L;
    private static final Long MISSION2 = 2L;
    private static final Long MISSION3 = 3L;

    private static final Long LEVEL1 = 1L;
    private static final Long LEVEL2 = 2L;
    private static final Long LEVEL3 = 3L;

    private static final String JOANNE = "조앤";
    private static final String BROWN = "브라운";

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
