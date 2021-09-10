package wooteco.prolog.studylog.studylog.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import wooteco.prolog.studylog.application.StudylogDocumentService;
import wooteco.prolog.studylog.domain.StudylogDocument;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;
import wooteco.support.utils.IntegrationTest;

// TODO
@IntegrationTest
class StudylogDocumentServiceTest {

    private static String 검색어 = "테스트";
    private static String 띄어쓰기가_포함된_검색어 = "테스트 하자";

    @Autowired
    private StudylogDocumentService studyLogDocumentService;

    @BeforeEach
    void setUp() {
        studyLogDocumentService.deleteAll();
//        saveAll(studylogDocuments);
    }

    @DisplayName("StudyDocument를 저장하고 조회한다")
    @Test
    void saveAndFindTest() {
        // 4L -> keyword가 타이틀에 일부만 포함됨
        // 9L -> keyword가 내용에 일부만 포함됨
        // 10L -> keyword가 일부라도 포함되지 않은 타이틀 및 내용
        // 11L -> keyword가 일부라도 포함되지 않은 타이틀 및 내용
        assertThat(studyLogDocumentService.findBySearchKeyword(검색어, PageRequest.of(0, 10)))
            .containsExactlyInAnyOrderElementsOf(
                Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L, 8L)
            );
    }

    private void saveAll(List<StudylogDocument> studylogDocuments) {
        for (StudylogDocument studyLogDocument : studylogDocuments) {
            studyLogDocumentService.save(studyLogDocument);
        }
    }

    @DisplayName("검색어에 띄어쓰기가 포함되어있으면 띄어쓰기 기준으로 단어를 나누어 검색한다.")
    @Test
    void saveAndFindTest2() {
        // 4L -> keyword가 타이틀에 일부만 포함됨
        // 9L -> keyword가 내용에 일부만 포함됨
        assertThat(
            studyLogDocumentService.findBySearchKeyword(띄어쓰기가_포함된_검색어, PageRequest.of(0, 10)))
            .containsExactlyInAnyOrderElementsOf(
                Arrays.asList(1L, 2L, 3L, 5L, 6L, 7L, 8L, 10L, 11L)
            );
    }

    @DisplayName("studylog를 수정한다. - 새로운 것을 삽입함으로써 대체한다.")
    @Test
    void update() {
        // given
        StudylogDocument studylogDocument = StudylogDocument.builder().build();
        studyLogDocumentService.save(studylogDocument);

        // when
        StudylogDocument updateStudylogDocument = StudylogDocument.builder().build();
        studyLogDocumentService.update(updateStudylogDocument);

        // then
        StudylogDocument findStudylogDocument = studyLogDocumentService.findById(1L);
        assertAll(
            () -> assertThat(findStudylogDocument.getId()).isEqualTo(1L),
            () -> assertThat(findStudylogDocument.getTitle()).isEqualTo(
                updateStudylogDocument.getTitle()),
            () -> assertThat(findStudylogDocument.getContent()).isEqualTo(
                updateStudylogDocument.getContent())
        );
    }

    @DisplayName("studylog를 삭제한다.")
    @Test
    void delete() {
        // given
        StudylogDocument studylogDocument = StudylogDocument.builder().build();
        studyLogDocumentService.save(studylogDocument);

        // when
        studyLogDocumentService.delete(studylogDocument);

        // then
        assertThatThrownBy(() -> studyLogDocumentService.findById(1L))
            .isInstanceOf(StudylogDocumentNotFoundException.class);
    }
}
