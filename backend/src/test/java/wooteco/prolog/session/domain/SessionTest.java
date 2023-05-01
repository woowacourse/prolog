package wooteco.prolog.session.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.domain.Curriculum;

public class SessionTest {

    private final static String BACKEND_LEVEL1_SESSION = "백엔드 Java 레벨 1";
    private final static String OVER_LENGTH_NAME = "01234567890123456789012345678901234567890123456789";

    @DisplayName("Session(String name)의 인수로 최대 길이 이상의 문자열을 넣으면 TooLongLevelNameException이 발생한다")
    @Test
    public void Session_fail_because_over_length() {
        //given, when, then
        assertThatThrownBy(() -> new Session(OVER_LENGTH_NAME)).isInstanceOf(Exception.class);
    }

    @DisplayName("update(String name)의 인수로 최대 길이 이상의 문자열을 넣으면 예외가 발생한다.")
    @Test
    public void update_fail_because_over_length() {
        //given
        Session session = new Session(BACKEND_LEVEL1_SESSION);

        //when, then
        assertThatThrownBy(() -> session.update(OVER_LENGTH_NAME)).isInstanceOf(Exception.class);
    }

    @DisplayName("isSameAs(Curriculum curriculum)에 백엔드 커리큘럼을 넣어줬을 때 세션 이름에 Backend라는 단어가 포함되어있다면면 True를 반환한다")
    @Test
    public void isSameAs_true() {
        //given
        Session session = new Session(BACKEND_LEVEL1_SESSION);

        //when, then
        assertThat(session.isSameAs(Curriculum.BACKEND)).isTrue();
    }

    @DisplayName("equals(Object object)는 세션 아이디가 동일한 세션을 비교할 경우 true를 반환한다")
    @Test
    public void equals_true_because_id() {
        //given
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);
        Session comparison = new Session(BACKEND_LEVEL1_SESSION);

        //when, then
        assertThat(criteria.equals(comparison)).isTrue();
    }

    @DisplayName("equals(Object object)는 주소가 동일한 세션을 비교할 경우 true를 반환한다")
    @Test
    public void equals_true_because_address() {
        //given
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);

        //when, then
        assertThat(criteria.equals(criteria)).isTrue();
    }

    @DisplayName("equals(Object object)는 다른 자료형의 객체와 세션을 비교할 경우 false를 반환한다")
    @Test
    public void equals_false_because_type() {
        //given
        Session criteria = new Session(BACKEND_LEVEL1_SESSION);
        Mission comparison = new Mission();

        //when, then
        assertThat(criteria.equals(comparison)).isFalse();
    }
}
