package wooteco.prolog.roadmap.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.roadmap.domain.Keyword;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.*;

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
    private Set<KeywordResponse> childrenKeywords;

    public KeywordResponse(final Long keywordId, final String name, final String description,
                           final int order, final int importance, final int totalQuizCount,
                           final int doneQuizCount, final Long parentKeywordId,
                           final List<RecommendedPostResponse> recommendedPosts,
                           final Set<KeywordResponse> childrenKeywords) {
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

    public KeywordResponse(final Long keywordId, final String name, final String description,
                           final int order,
                           final int importance, final Long parentKeywordId,
                           final Set<KeywordResponse> childrenKeywords) {
        this(keywordId, name, description, order, importance, 0, 0, parentKeywordId, emptyList(), childrenKeywords);
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
            keyword.getParentIdOrNull(),
            createChildren(keyword.getChildren()));
    }

    private static Set<KeywordResponse> createChildren(final Set<Keyword> children) {
        Set<KeywordResponse> keywords = new HashSet<>();
        for (Keyword keyword : children) {
            keywords.add(createWithAllChildResponse(keyword));
        }
        return keywords;
    }
}
