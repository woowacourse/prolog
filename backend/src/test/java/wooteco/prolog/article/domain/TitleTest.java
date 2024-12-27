package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import joptsimple.internal.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TitleTest {

    @ParameterizedTest(name = "타이틀이 \"{0}\" 일 때 예외가 발생한다")
    @NullSource
    @ValueSource(strings = {"", " "})
    void createTitle_fail(final String title) {
        Title result = new Title(title);
        assertThat(result.getTitle()).isEqualTo("제목없음");
    }

    @DisplayName("타이틀의 길이가 최대길이를 초과할 경우 예외가 발생한다")
    @Test
    void createTitle_fail_overLength() {
        //given
        final String title = Strings.repeat('.', 51);

        Title result = new Title(title);
        assertThat(result.getTitle()).isEqualTo(title.substring(0, 50));
    }

    @DisplayName("타이틀 앞뒤의 공백은 제거되어 저장된다.")
    @Test
    void trim() {
        //given
        final String articleTitle = "    title   ";

        //when
        final Title title = new Title(articleTitle);

        //then
        assertThat(title.getTitle()).isEqualTo("title");
    }
}
