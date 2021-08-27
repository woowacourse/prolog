package wooteco.prolog.studyLogDocument.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocumentRepository;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StudyLogDocumentServiceTest {

    private static String 포함된_단어 = "테스트";
    private static String 일부만_포함된_단어 = "테스";
    private static String 검색어 = "테스트";
    private static String 띄어쓰기가_포함된_검색어 = "테스트 하자";

    @Autowired
    private StudyLogDocumentService studyLogDocumentService;

    @Autowired
    private StudyLogDocumentRepository studyLogDocumentRepository;

    private StudyLogDocument studyLogDocument1;
    private StudyLogDocument studyLogDocument2;
    private StudyLogDocument studyLogDocument3;
    private StudyLogDocument studyLogDocument4;
    private StudyLogDocument studyLogDocument5;
    private StudyLogDocument studyLogDocument6;
    private StudyLogDocument studyLogDocument7;
    private StudyLogDocument studyLogDocument8;
    private StudyLogDocument studyLogDocument9;
    private StudyLogDocument studyLogDocument10;
    private StudyLogDocument studyLogDocument11;

    private List<StudyLogDocument> studyLogDocuments = new ArrayList<>();

    @BeforeEach
    void setUp() {
        studyLogDocumentRepository.deleteAll();

        studyLogDocument1 = new StudyLogDocument(1L, addFront("앞", 포함된_단어), "아예 다른 내용");
        studyLogDocument2 = new StudyLogDocument(2L, addLast(포함된_단어, "뒤"), "아예 다른 내용");
        studyLogDocument3 = new StudyLogDocument(3L, addBetween("앞", 포함된_단어, "뒤"), "아예 다른 내용");
        studyLogDocument4 = new StudyLogDocument(4L, 일부만_포함된_단어, "아예 다른 내용");
        studyLogDocument5 = new StudyLogDocument(5L, "아예 다른 단어", 포함된_단어);
        studyLogDocument6 = new StudyLogDocument(6L, "아예 다른 단어", addFront("앞", 포함된_단어));
        studyLogDocument7 = new StudyLogDocument(7L, "아예 다른 단어", addLast(포함된_단어, "뒤"));
        studyLogDocument8 = new StudyLogDocument(8L, "아예 다른 단어",
            addBetween("앞", 포함된_단어, "뒤"));
        studyLogDocument9 = new StudyLogDocument(9L, "아예 다른 단어", 일부만_포함된_단어);
        studyLogDocument10 = new StudyLogDocument(10L, "하자", "아예 다른 내용");
        studyLogDocument11 = new StudyLogDocument(11L, "아예 다른 단어", "하자");

        studyLogDocuments.addAll(
            Arrays.asList(studyLogDocument1, studyLogDocument2, studyLogDocument3,
                studyLogDocument4, studyLogDocument5, studyLogDocument6, studyLogDocument7,
                studyLogDocument8, studyLogDocument9, studyLogDocument10, studyLogDocument11));
    }

    private String addFront(String front, String word) {
        return front + " " + word;
    }

    private String addLast(String word, String back) {
        return word + " " + back;
    }

    private String addBetween(String front, String word, String back) {
        return front + " " + word + " " + back;
    }

    @DisplayName("StudyDocument를 저장하고 조회한다")
    @Test
    void saveAndFindTest() {
        saveAll(studyLogDocuments);

        // 4L -> keyword가 타이틀에 일부만 포함됨
        // 9L -> keyword가 내용에 일부만 포함됨
        // 10L -> keyword가 일부라도 포함되지 않은 타이틀 및 내용
        // 11L -> keyword가 일부라도 포함되지 않은 타이틀 및 내용
        assertThat(studyLogDocumentService.findBySearchKeyword(검색어, PageRequest.of(0, 10)))
            .containsExactlyInAnyOrderElementsOf(
                Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L, 8L)
            );
    }

    private void saveAll(List<StudyLogDocument> studyLogDocuments) {
        for (StudyLogDocument studyLogDocument : studyLogDocuments) {
            studyLogDocumentService.save(studyLogDocument);
        }
    }

    @DisplayName("검색어에 띄어쓰기가 포함되어있으면 띄어쓰기 기준으로 단어를 나누어 검색한다.")
    @Test
    void saveAndFindTest2() {
        saveAll(studyLogDocuments);

        // 4L -> keyword가 타이틀에 일부만 포함됨
        // 9L -> keyword가 내용에 일부만 포함됨
        assertThat(studyLogDocumentService.findBySearchKeyword(띄어쓰기가_포함된_검색어, PageRequest.of(0, 10)))
            .containsExactlyInAnyOrderElementsOf(
                Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L, 8L, 10L, 11L)
            );
    }
}