package wooteco.prolog.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.article.application.OgTagParser.OgType;

public class OgTagParserTest {

    private final OgTagParser ogTagParser = new OgTagParser();

    @Test
    @DisplayName("image와 title을 파싱한다.")
    void parseTags() throws IOException {
        // "https://www.woowahan.com/" 사이트에 요청을 하기에 외부 의존성이 존재하는 테스트입니다.

        //given
        final String url = "https://www.woowahan.com/";
        final Map<OgType, String> expected = new HashMap<>();
        expected.put(OgType.IMAGE, "https://woowahan-cdn.woowahan.com/static/image/share_kor.jpg");
        expected.put(OgType.TITLE, "우아한형제들");

        //when
        final Map<OgType, String> actual = ogTagParser.parse(url);

        //then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
