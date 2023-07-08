package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import joptsimple.internal.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import wooteco.prolog.common.exception.BadRequestException;

class TitleTest {

    @ParameterizedTest(name = "타이틀이 {0} 일 때 예외 발생")
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void createTitle_fail(final String title) {
        assertThatThrownBy(() -> new Title(title))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("타이틀의 길이가 최대길이를 초과할 경우 예외발생")
    @Test
    void createTitle_fail_overLength() {
        final String title = Strings.repeat('.', 51);
        assertThatThrownBy(() -> new Title(title))
            .isInstanceOf(BadRequestException.class);
    }

}
