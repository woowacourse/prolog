package wooteco.prolog.roadmap.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.NotFoundErrorCodeException;

class KeywordTest {

    private static final long SESSION_ID = 1L;

    @Nested
    class 초기화를_테스트 {

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
    }
}
