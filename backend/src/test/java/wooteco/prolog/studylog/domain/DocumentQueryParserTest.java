package wooteco.prolog.studylog.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.studylog.domain.DocumentQueryParser.removeSpecialChars;

class DocumentQueryParserTest {

    private final List<String> escapedChars = Arrays.asList(
        "?", "!", "/", "\\", "!", "+", "-", "*", "[", "]", "{", "}", "&", "|", "(", ")", ":"
    );

    @DisplayName("escapedChars가 들어오면 제거한다.")
    @Test
    void escapeCharacters() {
        // given
        List<String> results = removeSpecialChars(escapedChars);

        // when - then
        for (String result : results) {
            assertThat(result).isEqualTo(" ");
        }
    }

    @DisplayName("escapedChars가 들어오면 제거한다 - 단어 앞에 붙은 경우")
    @Test
    void escapeCharactersFront() {
        // given
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < escapedChars.size(); i++) {
            keywords.add(escapedChars.get(i) + "안녕");
        }

        List<String> results = removeSpecialChars(keywords);

        // when - then
        for (String result : results) {
            assertThat(result).isEqualTo("안녕");
        }
    }

    @DisplayName("escapedChars가 들어오면 제거한다 - 단어 사이에 붙은 경우")
    @Test
    void escapeCharactersMiddle() {
        // given
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < escapedChars.size(); i++) {
            keywords.add("1" + escapedChars.get(i) + "1");
        }

        List<String> results = removeSpecialChars(keywords);

        // when - then
        for (String result : results) {
            assertThat(result).isEqualTo("11");
        }
    }

    @DisplayName("escapedChars가 들어오면 제거한다 - 단어가 사이에 붙은 경우")
    @Test
    void escapeCharactersMiddle2() {
        // given
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < escapedChars.size(); i++) {
            keywords.add(escapedChars.get(i) + "1" + escapedChars.get(i));
        }

        List<String> results = removeSpecialChars(keywords);

        // when - then
        for (String result : results) {
            assertThat(result).isEqualTo("1");
        }
    }

    @DisplayName("escapedChars가 들어오면 제거한다 - 단어 뒤에 붙은 경우")
    @Test
    void escapeCharactersBack() {
        // given
        List<String> keywords = new ArrayList<>();
        for (int i = 0; i < escapedChars.size(); i++) {
            keywords.add("안녕" + escapedChars.get(i));
        }

        List<String> results = removeSpecialChars(keywords);

        // when - then
        for (String result : results) {
            assertThat(result).isEqualTo("안녕");
        }
    }

    @DisplayName("input으로 들어오는 List를 OR로 엮는다. - 비어있는 경우 와일드카드를 리턴한다.")
    @Test
    void makeDefaultQueryStringWhenEmpty() {
        // given
        String result = DocumentQueryParser.makeDefaultQueryString(emptyList());
        // when -  then
        assertThat(result).isEqualTo("*");
    }

    @DisplayName("input으로 들어오는 List를 OR로 엮는다. - null인 경우 와일드카드를 리턴한다.")
    @Test
    void makeDefaultQueryStringWhenNull() {
        // given
        String result = DocumentQueryParser.makeDefaultQueryString(null);
        // when -  then
        assertThat(result).isEqualTo("*");
    }

    @DisplayName("input으로 들어오는 List를 OR로 엮는다.")
    @Test
    void makeDefaultQueryString() {
        // given
        String result = DocumentQueryParser.makeDefaultQueryString(
            Arrays.asList("joanne", "brown")
        );
        // when -  then
        assertThat(result).isEqualTo("joanne OR brown");
    }

    @DisplayName("input으로 들어오는 List를 OR로 엮는다. - 하나인 경우 그대로 반환한다.")
    @Test
    void makeDefaultQueryStringSingle() {
        // given
        String result = DocumentQueryParser.makeDefaultQueryString(
            List.of("joanne")
        );
        // when -  then
        assertThat(result).isEqualTo("joanne");
    }

    @DisplayName("검색 키워드로 들어오는 List에 와일드카드를 포함한다. - 키워드가 비어있는 경우 와일드카드를 리턴한다.")
    @Test
    void makeKeywordsQueryStringEmpty() {
        // given
        String keyword = DocumentQueryParser.makeKeywordsQueryString(
            emptyList()
        );
        // when - then
        assertThat(keyword).isEqualTo("*");
    }

    @DisplayName("검색 키워드로 들어오는 List에 와일드카드를 포함한다. - 키워드가 null인 경우 와일드카드를 리턴한다.")
    @Test
    void makeKeywordsQueryStringNull() {
        // given
        String keyword = DocumentQueryParser.makeKeywordsQueryString(
            null
        );
        // when - then
        assertThat(keyword).isEqualTo("*");
    }

    @DisplayName("검색 키워드로 들어오는 List에 와일드카드를 포함한다.")
    @Test
    void makeKeywordsQueryStringSingle() {
        // given
        String keyword = DocumentQueryParser.makeKeywordsQueryString(
            List.of("java")
        );
        // when - then
        assertThat(keyword).isEqualTo("*java*");
    }

    @DisplayName("검색 키워드로 들어오는 List에 와일드카드를 포함한다. - 두 개 이상의 경우 와일드카드와 OR로 연결한다.")
    @Test
    void makeKeywordsQueryString() {
        // given
        String keyword = DocumentQueryParser.makeKeywordsQueryString(
            Arrays.asList("자바를", "잡아")
        );
        // when - then
        assertThat(keyword).isEqualTo("*자바를* OR *잡아*");
    }
}
