package wooteco.prolog.roadmap.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.RecommendedPost;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.RecommendedRepository;
import wooteco.prolog.roadmap.exception.KeywordNotFoundException;
import wooteco.prolog.roadmap.exception.RecommendedPostNotFoundException;

@Transactional(readOnly = true)
@Service
public class RecommendedService {

    private final RecommendedRepository recommendedRepository;
    private final KeywordRepository keywordRepository;

    public RecommendedService(final RecommendedRepository recommendedRepository, final KeywordRepository keywordRepository) {
        this.recommendedRepository = recommendedRepository;
        this.keywordRepository = keywordRepository;
    }

    @Transactional
    public Long create(final Long keywordId, final RecommendedRequest request) {
        final Keyword keyword = findKeywordOrThrow(keywordId);

        final RecommendedPost post = new RecommendedPost(request.getUrl());
        post.addKeyword(keyword);

        return recommendedRepository.save(post).getId();
    }

    private Keyword findKeywordOrThrow(final Long keywordId) {
        return keywordRepository.findById(keywordId)
            .orElseThrow(KeywordNotFoundException::new);
    }

    @Transactional
    public void update(final Long recommendedId, final RecommendedUpdateRequest request) {
        final RecommendedPost post = findPostOrThrow(recommendedId);

        post.updateUrl(request.getUrl());
    }

    private RecommendedPost findPostOrThrow(final Long recommendedId) {
        return recommendedRepository.findById(recommendedId)
            .orElseThrow(RecommendedPostNotFoundException::new);
    }

    @Transactional
    public void delete(final Long recommendedId) {
        final RecommendedPost recommendedPost = findPostOrThrow(recommendedId);
        recommendedPost.remove();
        recommendedRepository.delete(recommendedPost);
    }
}
