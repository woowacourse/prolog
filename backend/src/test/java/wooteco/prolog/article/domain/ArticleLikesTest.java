package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class ArticleLikesTest {

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
    @DisplayName("ArticleLike를 추가할 수 있다.")
    void addArticleLike() {
        //given
        final ArticleLikes articleLikes = new ArticleLikes();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleLike articleLike = new ArticleLike(article, member.getId());

        //when
        articleLikes.addLike(articleLike);

        //then
        assertThat(articleLikes.isAlreadyLike(member.getId()))
            .isTrue();
    }

    @Test
    @DisplayName("이미 추가되어 있으면 ArticleLike를 추가하지 않는다.")
    void addArticleLikeIfAlreadyAdded() {
        //given
        final ArticleLikes articleLikes = new ArticleLikes();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleLike articleLike = new ArticleLike(article, member.getId());
        articleLikes.addLike(articleLike);

        //when
        articleLikes.addLike(articleLike);

        //then
        assertThat(articleLikes.getArticleLikes()).hasSize(1);
    }

    @Test
    @DisplayName("ArticleLike를 삭제할 수 있다,")
    void removeArticleLike() {
        //given
        final ArticleLikes articleLikes = new ArticleLikes();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleLike articleLike = new ArticleLike(article, member.getId());
        articleLikes.addLike(articleLike);

        //when
        articleLikes.removeLike(member.getId());

        //then
        assertThat(articleLikes.isAlreadyLike(member.getId()))
            .isFalse();
    }

    @Test
    @DisplayName("ArticleBookmarks에 memberId를 넘겨서 해당 member가 아티클을 북마크했는지 확인한다.")
    void isAlreadyLike() {
        //given
        final ArticleLikes articleLikes = new ArticleLikes();
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleLike articleLike = new ArticleLike(article, member.getId());
        articleLikes.addLike(articleLike);

        //when
        final Boolean actual = articleLikes.isAlreadyLike(member.getId());

        //then
        assertThat(actual)
            .isTrue();
    }

}
