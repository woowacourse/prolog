package wooteco.prolog.roadmap.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.NotFoundErrorCodeException;
import wooteco.prolog.roadmap.exception.KeywordAndKeywordParentSameException;

class KeywordTest {

    private static final long SESSION_ID = 1L;
    private static final Keyword TEST_KEYWORD = new Keyword(2L, "자바", "자바입니다", 1, 1,
        SESSION_ID, null, null);

    @Nested
    class 객체_초기화_테스트 {

        @Test
        void 키워드를_정상적으로_초기화한다() {
            assertDoesNotThrow(() -> new Keyword(null, "자바", "자바입니다", 1, 1,
                SESSION_ID, null, null));
        }

        //KeywordSeqException 이 발생하지 않고, NotFoundErrorCodeException 발생
        @Test
        void seq값이_0보다_작거나_같으면_KeywordSeqException을_발생시킨다() {
            assertThatThrownBy(() -> new Keyword(null, "자바", "자바입니다", -1, 1,
                SESSION_ID, null, null))
                .isInstanceOf(NotFoundErrorCodeException.class);
        }
    }

    @Test
    void 키워드를_생성하는_기능_테스트() {
        final String keywordName = "자바";
        final String description = "자바에 대한 설명";
        final int seq = 1;
        final int importance = 1;

        final Keyword keyword = Keyword.createKeyword("자바", "자바에 대한 설명", 1,
            1, SESSION_ID, null);

        assertAll(
            () -> assertThat(keyword.getName())
                .isEqualTo(keywordName),
            () -> assertThat(keyword.getDescription())
                .isEqualTo(description),
            () -> assertThat(keyword.getSeq())
                .isEqualTo(seq),
            () -> assertThat(keyword.getImportance())
                .isEqualTo(importance)
        );
    }

    @Nested
    class 키워드를_업데이트하는_기능_테스트 {

        private static final String updateName = "List";
        private static final String updateDescription = "List에 대한 설명입니다.";
        private static final int updateSeq = 2;
        private static final int updateImportance = 2;

        private Keyword testKeyword;

        @BeforeEach
        void setUp() {
            testKeyword = new Keyword(2L, "자바", "자바입니다", 1, 1,
                SESSION_ID, null, null);
        }

        //TODO: 정상적으로 테스트하도록 수정하기
        @Test
        void 키워드를_정상적으로_업데이트한다() {
            testKeyword.update(updateName, updateDescription, updateSeq, updateImportance, null);

            assertAll(
                () -> assertThat(testKeyword.getName()).isEqualTo(updateName),
                () -> assertThat(testKeyword.getDescription()).isEqualTo(updateDescription),
                () -> assertThat(testKeyword.getSeq()).isEqualTo(updateSeq),
                () -> assertThat(testKeyword.getImportance()).isEqualTo(updateImportance)
            );
        }

        @Test
        void seq가_0보다_작거나_같으면_Exception이_발생한다() {
            final Runnable updateKeyword = () -> testKeyword.update(updateName, updateDescription, 0, updateImportance,
                null);

            assertThatThrownBy(updateKeyword::run)
                .isInstanceOf(NotFoundErrorCodeException.class);
        }

        /*
        아마 validateKeywordParent의 if문 조건이
        if(this.parent != null && this.id.equals(parentKeyword.getId()))
        에서
        if(parentKeyword != null && this.id.equals(parentKeyword.getId()))
        로 되야 할듯합니다.
         */
        @Test
        void 부모키워드가_자신과_같다면_Exception이_발생한다() {
            final Keyword keyword = new Keyword(3L, "컬렉션", "컬렉션에 대한 설명입니다", 1, 1
                , SESSION_ID, testKeyword, null);
            final Runnable updateKeyword = () -> keyword.update("List", "List에 대한 설명",
                1, 1, keyword);

            assertThatThrownBy(updateKeyword::run)
                .isInstanceOf(KeywordAndKeywordParentSameException.class);
        }
    }

    @Nested
    class 부모_키워드의_ID를_반환하는기능_테스트 {

        @Test
        void 부모키워드가_없는_경우() {
            final Long parentId = TEST_KEYWORD.getParentIdOrNull();

            assertThat(parentId)
                .isNull();
        }

        @Test
        void 부모키워드가_있는_경우() {
            final Keyword keyword = Keyword.createKeyword("컬렉션", "컬렉션에 대한 설명입니다.", 1, 1
                , SESSION_ID, TEST_KEYWORD);

            final Long parentId = keyword.getParentIdOrNull();

            assertThat(parentId)
                .isEqualTo(TEST_KEYWORD.getId());
        }
    }
}
