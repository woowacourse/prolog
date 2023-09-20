package wooteco.prolog.admin.roadmap.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.roadmap.domain.RecommendedPost;

@AllArgsConstructor
@Getter
public class RecommendedPostResponse {

    private final Long id;
    private final String url;

    public static RecommendedPostResponse from(final RecommendedPost recommendedPost) {
        return new RecommendedPostResponse(recommendedPost.getId(), recommendedPost.getUrl());
    }
}
