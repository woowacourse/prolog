package wooteco.prolog.article.domain;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import lombok.Getter;
import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import wooteco.prolog.member.domain.Member;

@Getter
public class RssFeed {
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private LocalDateTime publishedAt;

    public static RssFeed of(SyndEntry entry, String defaultImageUrl) {
        RssFeed rssFeed = new RssFeed();
        rssFeed.title = entry.getTitle();
        rssFeed.description = extractDescription(entry);
        rssFeed.url = entry.getLink();
        rssFeed.imageUrl = extractImageUrl(entry, defaultImageUrl);
        rssFeed.publishedAt = extractPublishedAt(entry);

        return rssFeed;
    }

    private static LocalDateTime extractPublishedAt(SyndEntry entry) {
        try {
            return entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
            return LocalDateTime.now();
        }
    }

    private static String extractDescription(SyndEntry entry) {
        try {
            if (entry.getDescription() != null) {
                return entry.getDescription().getValue();
            }

            Element groupElement = entry.getForeignMarkup().stream()
                .filter(it -> Objects.equals(it.getName(), "group"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

            Element descElement = groupElement.getChildren().stream()
                .filter(it -> Objects.equals(it.getName(), "description"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

            return descElement.getContent().get(0).getValue();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String extractImageUrl(SyndEntry entry, String defaultImageUrl) {
        String imageFromDescription = imageUrlFromDescription(entry);
        if (!imageFromDescription.isEmpty()) {
            return imageFromDescription;
        }

        String imageFromMedia = extractThumbnailFromMedia(entry);
        if (!imageFromMedia.isEmpty()) {
            return imageFromMedia;
        }

        return defaultImageUrl;
    }

    private static String extractThumbnailFromMedia(SyndEntry entry) {
        try {
            Element groupElement = entry.getForeignMarkup().stream()
                .filter(it -> Objects.equals(it.getName(), "group"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

            Element descElement = groupElement.getChildren().stream()
                .filter(it -> Objects.equals(it.getName(), "thumbnail"))
                .findFirst().orElseThrow(IllegalArgumentException::new);

            return descElement.getAttributeValue("url");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String imageUrlFromDescription(SyndEntry entry) {
        try {
            SyndContent descriptionContent = entry.getDescription();
            if (descriptionContent == null) {
                return "";
            }

            String description = descriptionContent.getValue();
            Document doc = Jsoup.parse(description);
            org.jsoup.nodes.Element img = doc.select("img").first();
            return img != null ? img.attr("src") : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDefaultImageUrl(SyndFeed feed) {
        try {
            return feed.getImage().getUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return "https://avatars.githubusercontent.com/u/45747236?s=200&v=4";
        }
    }

    public Article toArticle(Member member) {
        return new Article(
            member,
            new Title(title),
            new Description(description),
            new Url(url),
            new ImageUrl(imageUrl),
            publishedAt
        );
    }
}
