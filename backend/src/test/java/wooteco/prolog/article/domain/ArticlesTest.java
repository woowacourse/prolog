package wooteco.prolog.article.domain;

import org.junit.jupiter.api.Test;
import wooteco.prolog.article.application.SSLUtil;
import wooteco.prolog.member.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.member.domain.Role.CREW;

class ArticlesTest {
    @Test
    void fromRssFeedBy() {
        SSLUtil.disableSSLVerification();

        final Member member = new Member(1L, "username", "nickname", CREW, 1L, "url", null, "https://brunch.co.kr/rss/@@7vZS");
        Articles articles = Articles.fromRssFeedBy(member);
        assertThat(articles).isNotNull();
        assertThat(articles.getArticles()).hasSizeGreaterThan(1);
    }
}
