package wooteco.prolog.session.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.base.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.exception.TooLongMissionNameException;

class MissionTest {

    private static final Session TEST_SESSION = new Session(4L, 5L, "세션");

    @DisplayName("미션의 이름이 45자보다 길면 Exception 발생")
    @Test
    void validateMaxlengthTest() {
        //given
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 46; i++) {
            sb.append(".");
        }
        final Supplier<Mission> initialMission = () -> new Mission(sb.toString(), TEST_SESSION);

        //when, then
        assertThatThrownBy(initialMission::get).isInstanceOf(TooLongMissionNameException.class);
    }
}
