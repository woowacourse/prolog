package wooteco.prolog.roadmap.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import wooteco.prolog.common.exception.BadRequestException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_RECOMMENDED_POST_INVALID_URL_LENGTH;

class RecommendedPostTest {

    @Test
    @DisplayName("추천 포스트 생성 시 키워드가 null이면 예외가 발생한다")
    void construct_fail1() {
        assertThatThrownBy(() -> new RecommendedPost("https://example.com", null))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("추천 포스트 생성 시 url이 null이면 예외가 발생한다")
    void construct_fail2(final String url) {
        assertThatThrownBy(() -> new RecommendedPost(url, null))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("추천 포스트 생성 시 url의 길이가 공백 제외 0이면 예외가 발생한다")
    void construct_fail3() {
        //given
        final String url = "        ";

        //when, then
        assertThatThrownBy(() -> new RecommendedPost(url, null))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("추천 포스트 생성 시 url의 길이가 공백 제외 512보다 크면 예외가 발생한다")
    void construct_fail4() {
        //given
        final Keyword keyword = Keyword.createKeyword("name", "description", 1, 1, 1L, null);
        final String url = Stream.generate(() -> "a")
            .limit(513)
            .collect(Collectors.joining());

        //when, then
        assertThatThrownBy(() -> new RecommendedPost(url, keyword))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(ROADMAP_RECOMMENDED_POST_INVALID_URL_LENGTH.getMessage());
    }

    @Test
    @DisplayName("추천 포스트 생성 테스트")
    void construct() {
        //given
        final Keyword keyword = Keyword.createKeyword("name", "description", 1, 1, 1L, null);
        final String url = "http://www.salmon";

        //when, then
        assertDoesNotThrow(() -> new RecommendedPost(url, keyword));
    }

    @Test
    @DisplayName("삭제 기능 테스트")
    void remove() {
        //given
        final Keyword keyword = Keyword.createKeyword("이름", "설명", 1, 1, 1L, null);
        final RecommendedPost recommendedPost = new RecommendedPost("https://example.com", keyword);

        //when
        recommendedPost.remove();

        //then
        assertThat(keyword.getRecommendedPosts()).isEmpty();
    }

    @Test
    @DisplayName("소속 키워드를 추가한다")
    void addKeyword() {
        //given
        final Keyword keyword = Keyword.createKeyword("name", "description", 1, 1, 1L, null);
        final RecommendedPost post = new RecommendedPost("http://연어", keyword);

        //when
        post.addKeyword(keyword);

        //then
        assertSoftly(soft -> {
            assertThat(post.getKeyword()).isEqualTo(keyword);
            assertThat(keyword.getRecommendedPosts()).containsExactly(post);
        });
    }
}
