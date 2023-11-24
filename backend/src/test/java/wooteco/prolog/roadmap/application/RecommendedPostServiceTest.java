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

import static org.assertj.core.api.Assertions.assertThat;

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
        final RecommendedRequest request = new RecommendedRequest("https://example.com");

        //when
        recommendedPostService.create(keyword.getId(), request);

        //then
        assertThat(recommendedPostRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("추천 포스트 수정 테스트")
    void update() {
        //given
        final Long recommendedPostId = recommendedPostService.create(
            keyword.getId(),
            new RecommendedRequest("https://example.com"));

        final String newUrl = "https://new.com";
        final RecommendedUpdateRequest updateRequest = new RecommendedUpdateRequest(newUrl);

        //when
        recommendedPostService.update(recommendedPostId, updateRequest);

        //then
        final RecommendedPost post = recommendedPostRepository.findById(recommendedPostId).get();
        assertThat(post.getUrl()).isEqualTo(newUrl);
    }

    @Test
    @DisplayName("추천 포스트 삭제 테스트")
    void delete() {
        //given
        final RecommendedRequest request = new RecommendedRequest("https://example.com");
        final Long recommendedPostId = recommendedPostService.create(keyword.getId(), request);

        //when
        recommendedPostService.delete(recommendedPostId);

        //then
        assertThat(recommendedPostRepository.existsById(recommendedPostId)).isFalse();
    }
}
