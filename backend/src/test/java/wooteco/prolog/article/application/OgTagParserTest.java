package wooteco.prolog.article.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.article.application.OgTagParser.OgType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class OgTagParserTest {

    private final OgTagParser ogTagParser = new OgTagParser();

    @Test
    @DisplayName("image와 title을 파싱한다.")
    void parseTags() {
        //given
        final String url = "https://velog.io/@hong-sile/OAuth-2.0-%EA%B0%9C%EB%85%90%EA%B3%BC-%EC%84%A4%EA%B3%84-%EC%9D%B4%EC%9C%A0";
        final Map<OgType, String> expected = new HashMap<>();
        expected.put(OgType.IMAGE, "https://velog.velcdn.com/images/hong-sile/post/fa53be22-2cb2-4a18-b6f8-cf014957d3e2/image.png");
        expected.put(OgType.TITLE, "OAuth 2.0 개요");

        //when
        final Map<OgType, String> actual = ogTagParser.parse(url);

        //then
        assertThat(actual)
            .usingRecursiveComparison()
            .isEqualTo(expected);
    }
}
