package wooteco.prolog.studylog.tag.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.studylog.domain.TagName;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.prolog.common.exception.BadRequestCode.TAG_NAME_NULL_OR_EMPTY;

class TagNameTest {

    @DisplayName("값이 null이거나 empty면 예외")
    @Test
    void tagNameNullOrEmptyTest() {
        //given
        String empty = "";
        String justBlank = "    ";
        //when
        //then
        assertThatThrownBy(() -> new TagName(empty))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(TAG_NAME_NULL_OR_EMPTY.getMessage());
        assertThatThrownBy(() -> new TagName(justBlank))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(TAG_NAME_NULL_OR_EMPTY.getMessage());
        assertThatThrownBy(() -> new TagName(null))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(TAG_NAME_NULL_OR_EMPTY.getMessage());
    }

    @DisplayName("생성")
    @Test
    void createTitleTest() {
        //given
        String expectedText = "자바";

        //when
        TagName TagName1 = new TagName(expectedText);
        TagName TagName2 = new TagName("    " + expectedText);
        TagName TagName3 = new TagName(expectedText + "   ");

        //then
        assertThat(TagName1.getValue()).isEqualTo(expectedText);
        assertThat(TagName2.getValue()).isEqualTo(expectedText);
        assertThat(TagName3.getValue()).isEqualTo(expectedText);
    }
}
