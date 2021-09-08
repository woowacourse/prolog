package wooteco.prolog.post.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.exception.PostContentNullOrEmptyException;
import wooteco.prolog.studylog.domain.Content;

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
            .isInstanceOf(PostContentNullOrEmptyException.class);
        assertThatThrownBy(() -> new Content(justBlank))
            .isInstanceOf(PostContentNullOrEmptyException.class);
        assertThatThrownBy(() -> new Content(null))
            .isInstanceOf(PostContentNullOrEmptyException.class);
    }
}
