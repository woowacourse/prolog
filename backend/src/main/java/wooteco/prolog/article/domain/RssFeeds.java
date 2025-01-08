package wooteco.prolog.article.domain;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RssFeeds {

    private final List<RssFeed> rssFeeds;

    public RssFeed findLatestRssFeed() {
        return rssFeeds.stream()
            .max(Comparator.comparing(RssFeed::getPublishedAt))
            .orElse(null);
    }

    public boolean isEmpty() {
        return rssFeeds.isEmpty();
    }

    public List<RssFeed> findArticlesAfter(LocalDateTime latestArticlePublishedAt) {
        return rssFeeds.stream()
            .filter(rssFeed -> rssFeed.getPublishedAt().isAfter(latestArticlePublishedAt))
            .collect(toList());
    }
}
