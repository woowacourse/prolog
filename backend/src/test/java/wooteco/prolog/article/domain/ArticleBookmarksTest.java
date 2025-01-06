package wooteco.prolog.article.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleBookmarksTest {

    private Member member;
    private Title title;
    private Url url;
    private ImageUrl imageUrl;

    @BeforeEach
    void setUp() {
        member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");
        title = new Title("title");
        url = new Url("url");
        imageUrl = new ImageUrl("imageUrl");
    }

    @Test
    @DisplayName("ArticleBookmark를 추가할 수 있다.")
    void addArticleBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleBookmark articleBookmark = new ArticleBookmark(article, member.getId());

        //when
        articleBookmarks.addBookmark(articleBookmark);

        //then
        assertThat(articleBookmarks.containBookmark(member.getId()))
            .isTrue();
    }

    @Test
    @DisplayName("이미 추가되어 있으면 ArticleBookmark를 추가하지 않는다.")
    void addArticleBookmarkIfAlreadyAdded() {
        //given
        final ArticleBookmarks articleLikes = new ArticleBookmarks();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleBookmark articleLike = new ArticleBookmark(article, member.getId());
        articleLikes.addBookmark(articleLike);

        //when
        articleLikes.addBookmark(articleLike);

        //then
        assertThat(articleLikes.getArticleBookmarks()).hasSize(1);
    }

    @Test
    @DisplayName("ArticleBookmark를 삭제할 수 있다,")
    void removeArticleBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleBookmark articleBookmark = new ArticleBookmark(article, member.getId());
        articleBookmarks.addBookmark(articleBookmark);

        //when
        articleBookmarks.removeBookmark(member.getId());

        //then
        assertThat(articleBookmarks.containBookmark(member.getId()))
            .isFalse();
    }

    @Test
    @DisplayName("ArticleBookmarks에 memberId를 넘겨서 해당 member가 아티클을 북마크했는지 확인한다.")
    void containBookmark() {
        //given
        final ArticleBookmarks articleBookmarks = new ArticleBookmarks();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleBookmark articleBookmark = new ArticleBookmark(article, member.getId());
        articleBookmarks.addBookmark(articleBookmark);

        //when
        final Boolean actual = articleBookmarks.containBookmark(member.getId());

        //then
        assertThat(actual)
            .isTrue();
    }
}
