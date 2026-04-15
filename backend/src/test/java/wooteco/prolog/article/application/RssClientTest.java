package wooteco.prolog.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import wooteco.prolog.article.domain.RssFeeds;

class RssClientTest {

    @Test
    void fromTistoryRssFeedBy() {
        RssClient rssClient = new RssClient();
        String feedUrl = getClass().getClassLoader().getResource("rss/tistory-feed.xml").toString();

        RssFeeds rssFeeds = rssClient.fromRssFeedBy(feedUrl);

        assertThat(rssFeeds.getRssFeeds()).isNotEmpty();
        assertThat(rssFeeds.getRssFeeds().get(0).getTitle()).isEqualTo("Test Article");
    }

    @Test
    void fromYoutubeRssFeedBy() {
        RssClient rssClient = new RssClient();
        String feedUrl = getClass().getClassLoader().getResource("rss/youtube-feed.xml").toString();

        RssFeeds rssFeeds = rssClient.fromRssFeedBy(feedUrl);

        assertThat(rssFeeds.getRssFeeds()).isNotEmpty();
        assertThat(rssFeeds.getRssFeeds().get(0).getTitle()).isEqualTo("Test Video");
    }

    @Test
    void fromInvalidRssFeedBy() {
        RssClient rssClient = new RssClient();

        RssFeeds rssFeeds = rssClient.fromRssFeedBy("https://invalid.example.com/nonexistent-feed");

        assertThat(rssFeeds.getRssFeeds()).isEmpty();
    }

    @Test
    void fromEmptyRssFeedBy() {
        RssClient rssClient = new RssClient();
        String feedUrl = getClass().getClassLoader().getResource("rss/empty-feed.xml").toString();

        RssFeeds rssFeeds = rssClient.fromRssFeedBy(feedUrl);

        assertThat(rssFeeds.getRssFeeds()).isEmpty();
    }
}
