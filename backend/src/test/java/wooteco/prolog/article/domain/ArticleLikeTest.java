package wooteco.prolog.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class ArticleLikeTest {

    @DisplayName("memberId가 동일한지 확인")
    @Test
    void isOwner() {
        //given
        final Member member = new Member(11L, "username", "nickname", Role.CREW, 101L, "https://");
        final Title title = new Title("title");
        final Url url = new Url("url");
        final ImageUrl imageUrl = new ImageUrl("imageUrl");
        final Article article = new Article(member, title, url, imageUrl);
        final ArticleLike articleLike = new ArticleLike(article, member.getId());

        //when
        final Boolean actual = articleLike.isOwner(member.getId());

        //then
        assertThat(actual)
            .isTrue();
    }
}
