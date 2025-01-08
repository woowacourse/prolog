package wooteco.prolog.article.application;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;
import wooteco.prolog.article.domain.RssFeed;
import wooteco.prolog.article.domain.RssFeeds;

import java.net.URL;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

@Component
public class RssClient {
    public RssFeeds fromRssFeedBy(String feedUrl) {
        try {
            URL feedSource = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(feedSource));
            String defaultImageUrl = RssFeed.extractDefaultImageUrl(syndFeed);

            return new RssFeeds(syndFeed.getEntries().stream()
                .map(it -> RssFeed.of(it, defaultImageUrl))
                .collect(toList()));

        } catch (Exception e) {
            e.printStackTrace();
            return new RssFeeds(new ArrayList<>());
        }
    }
}
