package wooteco.prolog.roadmap.application.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.Keyword;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordResponse {

    private Long keywordId;
    private String name;
    private int order;
    private Long parentKeywordId;
    private String description;
    private List<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final int order, final Long parentKeywordId,
                           final String description, final List<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.order = order;
        this.parentKeywordId = parentKeywordId;
        this.description = description;
        this.childrenKeywords = childrenKeywords;
    }

    public static KeywordResponse createResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getSeq(),
            keyword.getParentIdOrNull(),
            keyword.getDescription(),
            null);
    }

    public static KeywordResponse createWithAllChildResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getSeq(),
            keyword.getParentIdOrNull(),
            keyword.getDescription(),
            createKeywordChild(keyword.getChildren()));
    }

    private static List<KeywordResponse> createKeywordChild(final List<Keyword> children) {
        List<KeywordResponse> keywords = new ArrayList<>();
        for (Keyword keyword : children) {
            keywords.add(createWithAllChildResponse(keyword));
        }
        return keywords;
    }
}
