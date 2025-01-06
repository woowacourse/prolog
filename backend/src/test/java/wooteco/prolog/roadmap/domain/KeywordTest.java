package wooteco.prolog.roadmap.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class KeywordTest {

    private static final Long SESSION_ID = 3L;
    private static final Keyword TEST_KEYWORD_JAVA = new Keyword(2L, "자바", "자바에 대한 설명", 1, 5,
        SESSION_ID, null, null);

    //KeywordSeqException 이 발생하지 않고, NotFoundErrorCodeException 발생, 원인 모르겠음...
    @DisplayName("seq값이 0보다 작거나 같으면 KeywordSeqException을_발생시킨다")
    @Test
    void validateSeqTest() {
        //given,when,then
        assertThatThrownBy(() -> new Keyword(1L, "자바", "자바입니다", -1, 1, SESSION_ID, null, null))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("update가 호출될 때,")
    @Nested
    class updateTest {

        private static final String updateName = "List";
        private static final String updateDescription = "List에 대한 설명입니다.";
        private static final int updateSeq = 2;
        private static final int updateImportance = 2;

        private Keyword testKeyword;

        @BeforeEach
        void setUp() {
            testKeyword = new Keyword(2L, "자바", "자바입니다", 1, 1, SESSION_ID, null, null);
        }

        @DisplayName("파라미터가 유효할 때 키워드를 정상적으로 업데이트한다.")
        @Test
        void success() {
            //when
            testKeyword.update(updateName, updateDescription, updateSeq, updateImportance, null);

            //then
            assertAll(() -> assertThat(testKeyword.getName()).isEqualTo(updateName),
                () -> assertThat(testKeyword.getDescription()).isEqualTo(updateDescription),
                () -> assertThat(testKeyword.getSeq()).isEqualTo(updateSeq),
                () -> assertThat(testKeyword.getImportance()).isEqualTo(updateImportance));
        }

        @DisplayName("seq가 0보다 작거나 같으면 Exception이 발생한다.")
        @Test
        void fail_seq_not_valid() {
            //given
            Runnable updateKeyword = () -> testKeyword.update(updateName, updateDescription, 0,
                updateImportance, null);

            //when,then
            assertThatThrownBy(updateKeyword::run)
                .isInstanceOf(BadRequestException.class);
        }

        /*
        아마 validateKeywordParent의 if문 조건이
        if(this.parent != null && this.id.equals(parentKeyword.getId()))
        에서
        if(parentKeyword != null && this.id.equals(parentKeyword.getId()))
        로 되야 할듯합니다.
         */
        @DisplayName("부모키워드가 자신과 같다면 Exception이 발생한다.")
        @Test
        void fail_parent_equal_keyword_self() {
            //given
            Keyword keyword = new Keyword(3L, "컬렉션", "컬렉션에 대한 설명입니다", 1, 1, SESSION_ID, testKeyword,
                null);
            Runnable updateKeyword = () -> keyword.update("List", "List에 대한 설명", 1, 1, keyword);

            //when, then
            assertThatThrownBy(updateKeyword::run)
                .isInstanceOf(BadRequestException.class);
        }
    }

    @DisplayName("getParentIdorNull이 호출될 때")
    @Nested
    class getParentIdOrNullTest {

        @DisplayName("부모 키워드가 없는 경우")
        @Test
        void return_null() {
            //when
            Long parentId = TEST_KEYWORD_JAVA.getParentIdOrNull();

            //then
            assertThat(parentId).isNull();
        }

        @DisplayName("부모 키워드가 존재하는 경우")
        @Test
        void return_id() {
            //given
            Keyword keyword = Keyword.createKeyword("컬렉션", "컬렉션에 대한 설명입니다.", 1, 1, SESSION_ID,
                TEST_KEYWORD_JAVA);

            //when
            Long parentId = keyword.getParentIdOrNull();

            //then
            assertThat(parentId).isEqualTo(TEST_KEYWORD_JAVA.getId());
        }
    }
}
