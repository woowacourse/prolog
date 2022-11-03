package wooteco.prolog.roadmap.application.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordResponse {

    private Long keywordId;
    private String name;
    private String description;
    private int order;
    private int importance;
    private Long parentKeywordId;
    private Set<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final String description, final int order,
                           final int importance, final Long parentKeywordId,
                           final Set<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.description = description;
        this.order = order;
        this.importance = importance;
        this.parentKeywordId = parentKeywordId;
        this.childrenKeywords = childrenKeywords;
    }
}
