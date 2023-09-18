package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import joptsimple.internal.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class ArticleTest {

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

    @DisplayName("아티클을 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> new Article(member, title, url, imageUrl));
    }

    @DisplayName("아티클을 작성자를 검증한다. (작성자일 때)")
    @Test
    void validateOwner_validOwner() {
        //given
        final Article article = new Article(member, title, url, imageUrl);

        //when
        //then
        assertDoesNotThrow(() -> article.validateOwner(member));
    }

    @DisplayName("아티클을 작성자를 검증한다. (작성자가 아닐 때)")
    @Test
    void validateOwner_invalidOwner() {
        //given
        final Article article = new Article(member, title, url, imageUrl);

        //when
        final Member member2 = new Member(2L, "user2", "nickname2", Role.CREW, 102L, "https://");
        //then
        assertThatThrownBy(() -> article.validateOwner(member2))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("아티클을 업데이트한다.")
    @Test
    void update_success() {
        //given
        final Article article = new Article(member, title, url, imageUrl);

        //when
        article.update("newTitle", "newUrl");

        //then
        assertThat(article.getTitle()).isEqualTo(new Title("newTitle"));
        assertThat(article.getUrl()).isEqualTo(new Url("newUrl"));
    }

    @DisplayName("유효하지 않은 제목으로 업데이트시 예외를 발생한다.")
    @Test
    void update_invalidTitle() {
        //given
        final Article article = new Article(member, title, url, imageUrl);

        //when
        //then
        assertThatThrownBy(() -> article.update(Strings.repeat('a', 51), "newUrl"))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("유효하지 않은 URL으로 업데이트시 예외를 발생한다.")
    @Test
    void update_invalidUrl() {
        //given
        final Article article = new Article(member, title, url, imageUrl);

        //when
        //then
        assertThatThrownBy(() -> article.update("newTitle", Strings.repeat('.', 1025)))
            .isInstanceOf(BadRequestException.class);
    }

    @Nested
    @DisplayName("멤버가 아티클의 북마크값을 세팅한다.")
    class setBookmark {

        @DisplayName("멤버가 아티클을 북마크로 등록한다.")
        @Test
        void addBookmark() {
            //given
            final Article article = new Article(member, title, url, imageUrl);

            //when
            article.setBookmark(member, true);

            //then
            final ArticleBookmarks articleBookmarks = article.getArticleBookmarks();
            final boolean contains = articleBookmarks.containBookmark(member.getId());

            assertThat(contains)
                .isTrue();
        }

        @DisplayName("멤버가 아티클의 북마크를 해제한다.")
        @Test
        void removeBookmark() {
            //given
            final Article article = new Article(member, title, url, imageUrl);
            article.setBookmark(member, true);

            //when
            article.setBookmark(member, false);

            //then
            final ArticleBookmarks articleBookmarks = article.getArticleBookmarks();
            final boolean contains = articleBookmarks.containBookmark(member.getId());

            assertThat(contains)
                .isFalse();
        }
    }
}
