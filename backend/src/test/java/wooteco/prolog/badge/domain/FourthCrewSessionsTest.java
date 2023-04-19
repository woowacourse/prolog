package wooteco.prolog.badge.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FourthCrewSessionsTest {

    @Test
    void 유효한_FourthCrewSessions_만_존재한다() {
        //expect
        Assertions.assertDoesNotThrow(() -> FourthCrewSessions.values());
    }

    @Test
    void getSessionIds_로_Id_들을_가져올_수_있다() {
        //expect
        Assertions.assertAll(
            () -> assertThat(FourthCrewSessions.LEVEL_THREE.getSessionIds()).hasSize(1),
            () -> assertThat(FourthCrewSessions.LEVEL_TWO.getSessionIds()).hasSize(2)
        );
    }
}
