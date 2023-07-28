package wooteco.prolog.roadmap.application.dto;

import lombok.Getter;
import wooteco.prolog.roadmap.domain.RecommendedPost;

@Getter
public class RecommendedPostResponse {

    private final String url;

    public RecommendedPostResponse(final String url) {
        this.url = url;
    }

    public static RecommendedPostResponse from(final RecommendedPost recommendedPost) {
        return new RecommendedPostResponse(recommendedPost.getUrl());
    }
}
