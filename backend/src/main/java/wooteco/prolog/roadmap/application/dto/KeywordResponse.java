package wooteco.prolog.roadmap.application.dto;

import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class KeywordResponse {

    private Long keywordId;
    private String name;
    private String description;
    private int order;
    private int importance;
    private int totalQuizCount;
    private int doneQuizCount;
    private Long parentKeywordId;
    private List<RecommendedPostResponse> recommendedPosts;
    private List<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final String description,
                           final int order, final int importance, final int totalQuizCount,
                           final int doneQuizCount, final Long parentKeywordId,
                           final List<RecommendedPostResponse> recommendedPosts,
                           final List<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.description = description;
        this.order = order;
        this.importance = importance;
        this.totalQuizCount = totalQuizCount;
        this.doneQuizCount = doneQuizCount;
        this.parentKeywordId = parentKeywordId;
        this.recommendedPosts = recommendedPosts;
        this.childrenKeywords = childrenKeywords;
    }

    public static KeywordResponse createResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            0, 0,
            keyword.getParentIdOrNull(),
            createRecommendedPostResponses(keyword),
            null);
    }

    private static List<RecommendedPostResponse> createRecommendedPostResponses(final Keyword keyword) {
        return keyword.getRecommendedPosts().stream()
            .map(RecommendedPostResponse::from)
            .collect(Collectors.toList());
    }

    public static KeywordResponse createWithAllChildResponse(final Keyword keyword) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            0,
            0,
            keyword.getParentIdOrNull(),
            createRecommendedPostResponses(keyword),
            createChildren(keyword.getChildren()));
    }

    private static List<KeywordResponse> createChildren(final Set<Keyword> children) {
        List<KeywordResponse> keywords = new ArrayList<>();
        for (Keyword keyword : children) {
            keywords.add(createWithAllChildResponse(keyword));
        }
        return keywords;
    }
}
