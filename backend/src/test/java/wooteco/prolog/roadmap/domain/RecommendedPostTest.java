package wooteco.prolog.roadmap.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class RecommendedPostTest {

    @Test
    @DisplayName("삭제 기능 테스트")
    void remove() {
        //given
        final Keyword keyword = Keyword.createKeyword("이름", "설명", 1, 1, 1L, null);
        final RecommendedPost recommendedPost = new RecommendedPost(1L, "https://example.com", null);
        recommendedPost.addKeyword(keyword);

        //when
        recommendedPost.remove();

        //then
        assertThat(keyword.getRecommendedPosts()).isEmpty();
    }

    @Test
    @DisplayName("소속 키워드를 추가한다")
    void addKeyword() {
        //given
        final RecommendedPost post = new RecommendedPost("http://연어");
        final Keyword keyword = Keyword.createKeyword("name", "description", 1, 1, 1L, null);

        //when
        post.addKeyword(keyword);

        //then
        assertSoftly(soft -> {
            assertThat(post.getKeyword()).isEqualTo(keyword);
            assertThat(keyword.getRecommendedPosts()).containsExactly(post);
        });
    }
}
