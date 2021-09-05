package wooteco.prolog.studyLogDocument.application;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.studyLogDocument.fixture.StudyLogDocumentTestFixture.studyLogDocuments;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.studyLogDocument.domain.StudyLogDocument;
import wooteco.support.utils.IntegrationTest;

@IntegrationTest
class StudyLogDocumentServiceTest {

    private static String 검색어 = "테스트";
    private static String 띄어쓰기가_포함된_검색어 = "테스트 하자";

    @Autowired
    private StudyLogDocumentService studyLogDocumentService;

    @BeforeEach
    void setUp() {
        studyLogDocumentService.deleteAll();
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
        assertThat(
            studyLogDocumentService.findBySearchKeyword(띄어쓰기가_포함된_검색어, PageRequest.of(0, 10)))
            .containsExactlyInAnyOrderElementsOf(
                Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L, 8L, 10L, 11L)
            );
    }
}