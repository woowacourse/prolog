package wooteco.prolog.admin.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordCreateRequest {

    private String name;
    private String description;
    private int order;
    private int importance;
    private Long parentKeywordId;

    public KeywordCreateRequest(final String name, final String description, final int order,
                                final int importance, final Long parentKeywordId) {
        this.name = name;
        this.description = description;
        this.order = order;
        this.importance = importance;
        this.parentKeywordId = parentKeywordId;
    }

    public Keyword toEntity(final Long sessionId, final Keyword keywordParent) {
        return Keyword.createKeyword(
            this.getName(), this.getDescription(), this.getOrder(),
            this.getImportance(), sessionId, keywordParent
        );
    }
}
