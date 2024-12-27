package wooteco.prolog.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import wooteco.prolog.article.domain.RssFeeds;

class RssClientTest {
    @Test
    void fromTistoryRssFeedBy() {
        RssClient rssClient = new RssClient();
        RssFeeds rssFeeds = rssClient.fromRssFeedBy("https://lazypazy.tistory.com/rss");

        assertThat(rssFeeds.getRssFeeds()).isNotEmpty();
    }

    @Test
    void fromYoutubeRssFeedBy() {
        SSLUtil.disableSSLVerification();

        RssClient rssClient = new RssClient();
        RssFeeds rssFeeds = rssClient.fromRssFeedBy("https://www.youtube.com/feeds/videos.xml?channel_id=UC-mOekGSesms0agFntnQang");

        assertThat(rssFeeds.getRssFeeds()).isNotEmpty();
    }

    @Test
    void fromVelogRssFeedBy() {
        SSLUtil.disableSSLVerification();

        RssClient rssClient = new RssClient();
        RssFeeds rssFeeds = rssClient.fromRssFeedBy("https://brunch.co.kr/rss/@@7vZS");

        assertThat(rssFeeds.getRssFeeds()).isNotEmpty();
    }

    @Test
    void fromInvalidRssFeedBy() {
        RssClient rssClient = new RssClient();
        RssFeeds rssFeeds = rssClient.fromRssFeedBy("https://v2.velog.io/rss/junho5336asdfasdf");

        assertThat(rssFeeds.getRssFeeds()).isEmpty();
    }
}
