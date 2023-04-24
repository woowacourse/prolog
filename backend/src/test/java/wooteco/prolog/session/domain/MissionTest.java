package wooteco.prolog.session.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static wooteco.prolog.studylog.StudylogFixture.TEST_SESSION;

import com.google.common.base.Supplier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.exception.TooLongMissionNameException;

class MissionTest {

    @Nested
    class 미션을_초기화하는_기능 {

        @Test
        void 미션의_이름이_45자보다_길면_Exception발생() {
            final StringBuilder sb = new StringBuilder();
            final String character = ".";
            for (int i = 0; i < 46; i++) {
                sb.append(character);
            }

            final Supplier<Mission> initialMission = () -> new Mission(sb.toString(), TEST_SESSION);

            assertThatThrownBy(initialMission::get).isInstanceOf(TooLongMissionNameException.class);
        }

        @Test
        void 정상적으로_Mission을_초기화하는_기능_테스트() {
            final Mission mission = new Mission(5L, "자바", TEST_SESSION);

            assertAll(() -> assertThat(mission.getId()).isEqualTo(5L),
                () -> assertThat(mission.getName()).isEqualTo("자바"),
                () -> assertThat(mission.getSession().getId()).isEqualTo(TEST_SESSION.getId()));
        }
    }
}
