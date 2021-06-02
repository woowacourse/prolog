package wooteco.prolog.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.post.exception.PostArgumentException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContentTest {
    @DisplayName("값이 null이거나 empty면 예외")
    @Test
    void contentTest() {
        //given
        String empty = "";
        String justBlank = "    ";
        //when
        //then
        assertThatThrownBy(() -> new Content(empty))
                .isInstanceOf(PostArgumentException.class);
        assertThatThrownBy(() -> new Content(justBlank))
                .isInstanceOf(PostArgumentException.class);
        assertThatThrownBy(() -> new Content(null))
                .isInstanceOf(PostArgumentException.class);
    }
}