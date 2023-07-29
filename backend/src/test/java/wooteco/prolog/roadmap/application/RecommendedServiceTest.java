package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.RecommendedPost;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.RecommendedRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
@Transactional
class RecommendedServiceTest {

    @Autowired
    private RecommendedService recommendedService;
    @Autowired
    private RecommendedRepository recommendedRepository;
    @Autowired
    private KeywordRepository keywordRepository;

    private Keyword keyword;

    @BeforeEach
    public void init() {
        final Keyword keyword = Keyword.createKeyword("이름", "설명", 1, 1, 1L, null);
        this.keyword = keywordRepository.save(keyword);
    }

    @Test
    @DisplayName("추천 포스트 생성 테스트")
    void create() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https//:example.com");

        //when
        Long recommendedPostId = recommendedService.create(keyword.getId(), request);

        final Keyword persistedKeyword = keywordRepository.findById(keyword.getId()).get();
        final RecommendedPost persistedPost = recommendedRepository.findById(recommendedPostId).get();

        //then
        assertSoftly(softAssertions -> {
            assertThat(persistedPost.getUrl()).isEqualTo(request.getUrl());
            assertThat(persistedKeyword.getRecommendedPosts()).containsExactly(persistedPost);
        });
    }

    @Test
    @DisplayName("추천 포스트 수정 테스트")
    void update() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https//:example.com");
        Long recommendedPostId = recommendedService.create(keyword.getId(), request);
        String newUrl = "https//:example222.com";
        final RecommendedUpdateRequest updateRrequest = new RecommendedUpdateRequest(newUrl);

        //when
        recommendedService.update(recommendedPostId, updateRrequest);
        Optional<RecommendedPost> actual = recommendedRepository.findById(recommendedPostId);

        //then
        assertThat(actual.get().getUrl()).isEqualTo(newUrl);
    }

    @Test
    @DisplayName("추천 포스트 삭제 테스트")
    void delete() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https//:example.com");
        Long recommendedPostId = recommendedService.create(keyword.getId(), request);

        //when
        recommendedService.delete(recommendedPostId);

        //then
        assertSoftly(softAssertions -> {
            assertThat(recommendedRepository.findAll()).hasSize(0);
            assertThat(keywordRepository.findById(keyword.getId()).get().getRecommendedPosts())
                .isEmpty();
        });
    }
}