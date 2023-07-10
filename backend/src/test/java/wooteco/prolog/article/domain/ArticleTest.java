package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import joptsimple.internal.Strings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class ArticleTest {

    private Member member;
    private Title title;
    private Url url;

    @BeforeEach
    void setUp() {
        member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");
        title = new Title("title");
        url = new Url("url");
    }

    @DisplayName("아티클을 생성한다.")
    @Test
    void create_success() {
        //given
        //when
        //then
        assertDoesNotThrow(() -> new Article(member, title, url));
    }

    @DisplayName("아티클을 작성자를 검증한다. (작성자일 때)")
    @Test
    void validateOwner_validOwner() {
        //given
        final Article article = new Article(member, title, url);

        //when
        //then
        assertDoesNotThrow(() -> article.validateOwner(member));
    }

    @DisplayName("아티클을 작성자를 검증한다. (작성자가 아닐 때)")
    @Test
    void validateOwner_invalidOwner() {
        //given
        final Article article = new Article(member, title, url);

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
        final Article article = new Article(member, title, url);

        //when
        article.update("newTitle", "newUrl");

        //then
        Assertions.assertThat(article.getTitle()).isEqualTo(new Title("newTitle"));
        Assertions.assertThat(article.getUrl()).isEqualTo(new Url("newUrl"));
    }

    @DisplayName("유효하지 않은 제목으로 업데이트시 예외를 발생한다.")
    @Test
    void update_invalidTitle() {
        //given
        final Article article = new Article(member, title, url);

        //when
        //then
        assertThatThrownBy(() -> article.update(Strings.repeat('a', 51), "newUrl"))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("유효하지 않은 URL으로 업데이트시 예외를 발생한다.")
    @Test
    void update_invalidUrl() {
        //given
        final Article article = new Article(member, title, url);

        //when
        //then
        assertThatThrownBy(() -> article.update("newTitle", Strings.repeat('.', 1025)))
            .isInstanceOf(BadRequestException.class);
    }
}
