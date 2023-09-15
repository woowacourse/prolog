package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleBookmarkTest {

    @DisplayName("memberId가 동일한지 확인")
    @Test
    void isOwner() {
        //given
        final Long memberId = 2L;
        final ArticleBookmark articleBookmark = new ArticleBookmark(1L, memberId);

        //when
        final Boolean actual = articleBookmark.isOwner(memberId);

        //then
        assertThat(actual)
            .isTrue();
    }

}
