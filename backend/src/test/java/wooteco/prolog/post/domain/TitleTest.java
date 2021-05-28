package wooteco.prolog.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.post.exception.PostArgumentException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TitleTest {

    @DisplayName("값이 null이거나 empty면 예외")
    @Test
    void titleTest() {
        //given
        String empty = "";
        String justBlank = "    ";
        //when
        //then
        assertThatThrownBy(() -> new Title(empty))
                .isInstanceOf(PostArgumentException.class);
        assertThatThrownBy(() -> new Title(justBlank))
                .isInstanceOf(PostArgumentException.class);
        assertThatThrownBy(() -> new Title(null))
                .isInstanceOf(PostArgumentException.class);
    }

    @DisplayName("생성")
    @Test
    void createTitleTest() {
        //given
        String expectedText = "감자를 모양이 이쁘게 깎는 법";

        //when
        Title title1 = new Title(expectedText);
        Title title2 = new Title("    " +expectedText);
        Title title3 = new Title(expectedText + "   ");

        //then
        assertThat(title1.getTitle()).isEqualTo(expectedText);
        assertThat(title2.getTitle()).isEqualTo(expectedText);
        assertThat(title3.getTitle()).isEqualTo(expectedText);
    }

}