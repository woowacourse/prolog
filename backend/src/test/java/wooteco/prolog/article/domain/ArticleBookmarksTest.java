package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleBookmarksTest {

    @Test
    @DisplayName("ArticleBookmark를 추가할 수 있다.")
    void addArticleBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final ArticleBookmark articleBookmark = new ArticleBookmark(1L, 2L);

        //when
        articleBookmarks.addBookmark(articleBookmark);

        //then
        assertThat(articleBookmarks.containBookmark(2L))
            .isTrue();
    }

    @Test
    @DisplayName("ArticleBookmark를 삭제할 수 있다,")
    void removeArticleBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final Long memberId = 2L;
        final ArticleBookmark articleBookmark = new ArticleBookmark(1L, memberId);
        articleBookmarks.addBookmark(articleBookmark);

        //when
        articleBookmarks.removeBookmark(memberId);

        //then
        assertThat(articleBookmarks.containBookmark(memberId))
            .isFalse();
    }

    @Test
    @DisplayName("ArticleBookmarks에 memberId를 넘겨서 해당 member가 아티클을 북마크했는지 확인한다.")
    void containBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final Long memberId = 2L;
        final ArticleBookmark articleBookmark = new ArticleBookmark(1L, memberId);
        articleBookmarks.addBookmark(articleBookmark);

        //when
        final Boolean actual = articleBookmarks.containBookmark(memberId);

        //then
        assertThat(actual)
            .isTrue();
    }
}
