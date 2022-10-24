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
    private String description;
    private int order;
    private int importance;
    private Long parentKeywordId;
    private List<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final String description, final int order,
                           final int importance, final Long parentKeywordId,
                           final List<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.description = description;
        this.order = order;
        this.importance = importance;
        this.parentKeywordId = parentKeywordId;
        this.childrenKeywords = childrenKeywords;
    }

    public static KeywordResponse createResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            keyword.getParentIdOrNull(),
            null);
    }

    public static KeywordResponse createWithAllChildResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            keyword.getParentIdOrNull(),
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
