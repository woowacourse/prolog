package wooteco.prolog.session.domain;

import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.domain.Curriculum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {
    private final static String BACKEND_LEVEL1_SESSION = "백엔드 Java 레벨 1";

    private final static String OVER_LENGTH_NAME = "01234567890123456789012345678901234567890123456789";

    @Test
    public void 세션을_정상적으로_생성한다() {
        assertThat(new Session(BACKEND_LEVEL1_SESSION)).isNotNull();
    }

    @Test
    public void 세션을_정상적으로_수정한다() {
        Session session = new Session(BACKEND_LEVEL1_SESSION);
        assertThatThrownBy(() -> session.update(OVER_LENGTH_NAME)).isInstanceOf(Exception.class);
    }

    @Test
    public void 세션_이름이_최대_길이보다_크면_TooLongLevelNameException이_발생한다() {
        assertThatThrownBy(() -> new Session(OVER_LENGTH_NAME)).isInstanceOf(Exception.class);
    }

    @Test
    public void 세션이_백엔드_커리큘럼에_포함되면_true를_반환한다() {
        assertThat(new Session(BACKEND_LEVEL1_SESSION).isSameAs(Curriculum.BACKEND)).isTrue();
    }

    @Test
    public void 세션_아이디가_동일한_세션을_비교할_경우_equals는_true를_반환한다() {
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);
        Session comparison = new Session(BACKEND_LEVEL1_SESSION);
        assertThat(criteria.equals(comparison)).isTrue();
    }

    @Test
    public void 주소가_동일한_세션을_비교할_경우_equals는_true를_반환한다() {
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);
        assertThat(criteria.equals(criteria)).isTrue();
    }

    @Test
    public void 다른_자료형의_객체와_세션을_을_비교할_경우_equals는_true를_반환한다() {
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);
        Mission comparison = new Mission();
        assertThat(criteria.equals(comparison)).isFalse();
    }


}
