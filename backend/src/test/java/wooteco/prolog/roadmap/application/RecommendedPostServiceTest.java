package wooteco.prolog.roadmap.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wooteco.prolog.common.DataInitializer;
import wooteco.prolog.roadmap.application.dto.RecommendedRequest;
import wooteco.prolog.roadmap.application.dto.RecommendedUpdateRequest;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.RecommendedPost;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.domain.repository.RecommendedPostRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
class RecommendedPostServiceTest {

    @Autowired
    private RecommendedPostService recommendedPostService;
    @Autowired
    private RecommendedPostRepository recommendedPostRepository;
    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private DataInitializer dataInitializer;

    private Keyword keyword;

    @BeforeEach
    public void init() {
        final Session session = sessionRepository.save(new Session("레벨 1"));
        this.keyword = keywordRepository.save(Keyword.createKeyword("이름", "설명", 1, 1, session.getId(), null));
    }

    @AfterEach
    public void removeAll() {
        dataInitializer.execute();
    }

    @Test
    @DisplayName("추천 포스트 생성 테스트")
    void create() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https//:example.com");

        //when
        Long recommendedPostId = recommendedPostService.create(keyword.getId(), request);

        final Keyword persistedKeyword = keywordRepository.findById(keyword.getId()).get();
        final RecommendedPost persistedPost = recommendedPostRepository.findById(recommendedPostId).get();

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
        Long recommendedPostId = recommendedPostService.create(keyword.getId(), request);
        String newUrl = "https//:example222.com";
        final RecommendedUpdateRequest updateRrequest = new RecommendedUpdateRequest(newUrl);

        //when
        recommendedPostService.update(recommendedPostId, updateRrequest);
        Optional<RecommendedPost> actual = recommendedPostRepository.findById(recommendedPostId);

        //then
        assertThat(actual.get().getUrl()).isEqualTo(newUrl);
    }

    @Test
    @DisplayName("추천 포스트 삭제 테스트")
    void delete() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https://example.com");
        Long recommendedPostId = recommendedPostService.create(keyword.getId(), request);

        //when
        recommendedPostService.delete(recommendedPostId);

        //then
        assertSoftly(softAssertions -> {
            assertThat(recommendedPostRepository.findAll()).hasSize(0);
            assertThat(keywordRepository.findById(keyword.getId()).get().getRecommendedPosts())
                .isEmpty();
        });
    }
}
