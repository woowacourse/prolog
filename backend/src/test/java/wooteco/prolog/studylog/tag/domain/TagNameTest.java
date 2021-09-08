package wooteco.prolog.studylog.tag.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.studylog.domain.TagName;
import wooteco.prolog.studylog.exception.TagNameNullOrEmptyException;

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
            .isExactlyInstanceOf(TagNameNullOrEmptyException.class);
        assertThatThrownBy(() -> new TagName(justBlank))
            .isExactlyInstanceOf(TagNameNullOrEmptyException.class);
        assertThatThrownBy(() -> new TagName(null))
            .isExactlyInstanceOf(TagNameNullOrEmptyException.class);
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
