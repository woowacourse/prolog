package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import joptsimple.internal.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ImageUrlTest {

    @ParameterizedTest(name = "이미지 URL이 \"{0}\" 일 때")
    @NullSource
    @ValueSource(strings = {"", " "})
    void createUrl_fail(final String url) {
        ImageUrl imageUrl = new ImageUrl(url);
        assertThat(imageUrl.getUrl()).isNotBlank();
    }

    @DisplayName("이미지 URL의 길이가 최대길이를 초과할 경우")
    @Test
    void createUrl_fail_overLength() {
        final String url = Strings.repeat('.', 1025);
        ImageUrl imageUrl = new ImageUrl(url);
        assertThat(imageUrl.getUrl()).isNotBlank();
    }

    @DisplayName("이미지 URL 앞뒤의 공백은 제거되어 저장된다.")
    @Test
    void trim() {
        //given
        final String articleUrl = "    imageUrl   ";

        //when
        final ImageUrl imageUrl = new ImageUrl(articleUrl);

        //then
        assertThat(imageUrl.getUrl()).isEqualTo("imageUrl");
    }
}
