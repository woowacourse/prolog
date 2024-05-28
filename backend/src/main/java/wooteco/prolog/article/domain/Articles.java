package wooteco.prolog.article.domain;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import wooteco.prolog.member.domain.Member;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class Articles {
    private final List<Article> articles;

    public static Articles fromRssFeedBy(Member member) {
        try {
            URL feedSource = new URL(member.getRssFeedUrl());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed syndFeed = input.build(new XmlReader(feedSource));

            return new Articles(
                syndFeed.getEntries().stream()
                    .map(entry -> new Article(member,
                        new Title(entry.getTitle()),
                        new Description(entry.getDescription().getValue()),
                        new Url(entry.getLink()),
                        new ImageUrl(extractImageUrl(entry.getDescription().getValue()))))
                    .collect(toList())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new Articles(new ArrayList<>());
        }
    }

    public static String extractImageUrl(String description) {
        Document doc = Jsoup.parse(description);
        Element img = doc.select("img").first();
        return img != null ? img.attr("src") : null;
    }

    public List<Article> findNewArticles(List<Article> existedArticles) {
        return this.articles.stream()
            .filter(article -> !hasDuplicateUrl(existedArticles, article.getUrl().getUrl()))
            .collect(toList());
    }

    private boolean hasDuplicateUrl(List<Article> articles, String url) {
        return articles.stream()
            .anyMatch(article -> article.hasDuplicateUrl(url));
    }
}
