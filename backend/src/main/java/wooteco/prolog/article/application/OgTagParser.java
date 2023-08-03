package wooteco.prolog.article.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import wooteco.prolog.common.exception.BadRequestCode;
import wooteco.prolog.common.exception.BadRequestException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OgTagParser {

    public enum OgType {
        TITLE("title"),
        IMAGE("image");

        private static final String FORMAT = "meta[property=og:%s]";

        private final String metaTag;

        OgType(final String metaTag) {
            this.metaTag = metaTag;
        }

        public static Map<OgType, String> parseMap(final Document document) {
            return Arrays.stream(values())
                .collect(Collectors.toMap(
                    Function.identity()
                    , ogType -> getContent(ogType, document)
                ));
        }

        private static String getContent(final OgType type, final Document document) {
            final Element element = document.selectFirst(String.format(FORMAT, type.metaTag));
            return Optional.ofNullable(element)
                .map(it -> it.attr("content"))
                .orElse(null);
        }
    }

    public Map<OgType, String> parse(final String url) {
        try {
            final Document document = Jsoup.connect(url).get();

            return OgType.parseMap(document);
        } catch (Exception e) {
            throw new BadRequestException(BadRequestCode.ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION);
        }
    }
}
