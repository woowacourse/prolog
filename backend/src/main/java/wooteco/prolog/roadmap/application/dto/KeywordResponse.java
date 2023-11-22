package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
    private int answeredQuizCount;
    private Long parentKeywordId;
    private List<RecommendedPostResponse> recommendedPosts;
    private List<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final String description,
                           final int order, final int importance, final int totalQuizCount,
                           final int answeredQuizCount, final Long parentKeywordId,
                           final List<RecommendedPostResponse> recommendedPosts,
                           final List<KeywordResponse> childrenKeywords) {
        this.keywordId = keywordId;
        this.name = name;
        this.description = description;
        this.order = order;
        this.importance = importance;
        this.totalQuizCount = totalQuizCount;
        this.answeredQuizCount = answeredQuizCount;
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
            Collections.emptyList());
    }

    public static KeywordResponse createWithAllChildResponse(final Keyword keyword,
                                                             final Map<Long, Integer> totalQuizCounts,
                                                             final Map<Long, Integer> answeredQuizCounts) {
        return new KeywordResponse(
            keyword.getId(),
            keyword.getName(),
            keyword.getDescription(),
            keyword.getSeq(),
            keyword.getImportance(),
            totalQuizCounts.getOrDefault(keyword.getId(), 0),
            answeredQuizCounts.getOrDefault(keyword.getId(), 0),
            keyword.getParentIdOrNull(),
            createRecommendedPostResponses(keyword),
            createChildren(keyword.getChildren(), totalQuizCounts, answeredQuizCounts));
    }

    private static List<RecommendedPostResponse> createRecommendedPostResponses(final Keyword keyword) {
        return keyword.getRecommendedPosts().stream()
            .map(RecommendedPostResponse::from)
            .collect(Collectors.toList());
    }

    private static List<KeywordResponse> createChildren(final Set<Keyword> children,
                                                        final Map<Long, Integer> totalQuizCounts,
                                                        final Map<Long, Integer> answeredQuizCounts) {
        return children.stream()
            .map(keyword -> createWithAllChildResponse(keyword, totalQuizCounts, answeredQuizCounts))
            .collect(Collectors.toList());
    }
}
