package wooteco.prolog.studylog.studylog.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.prolog.common.exception.BadRequestCode.STUDYLOG_CONTENT_NULL_OR_EMPTY;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.studylog.domain.Content;

class ContentTest {

    @DisplayName("값이 null이거나 empty면 예외")
    @Test
    void contentTest() {
        //given
        String empty = "";
        String justBlank = "    ";
        //when, then
        assertThatThrownBy(() -> new Content(empty))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_CONTENT_NULL_OR_EMPTY.getMessage());
        assertThatThrownBy(() -> new Content(justBlank))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_CONTENT_NULL_OR_EMPTY.getMessage());
        assertThatThrownBy(() -> new Content(null))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(STUDYLOG_CONTENT_NULL_OR_EMPTY.getMessage());
    }
}
