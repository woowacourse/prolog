package wooteco.prolog.badge.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

class FourthCrewSessionsTest {

    @Test
    void 유효한_FourthCrewSessions_만_존재한다() {
        //expect
        assertDoesNotThrow(FourthCrewSessions::values);
    }

    @Test
    void getSessionIds_로_Id_들을_가져올_수_있다() {
        //expect
        assertAll(
            () -> assertThat(FourthCrewSessions.LEVEL_THREE.getSessionIds()).hasSize(1),
            () -> assertThat(FourthCrewSessions.LEVEL_TWO.getSessionIds()).hasSize(2)
        );
    }
}
