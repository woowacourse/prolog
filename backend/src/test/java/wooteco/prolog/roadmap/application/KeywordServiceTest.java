package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.Keyword;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.repository.KeywordRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.IntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@IntegrationTest
class KeywordServiceTest {

    private final KeywordService keywordService;
    private final SessionRepository sessionRepository;

    private final KeywordRepository keywordRepository;
    private final EntityManager em;

    public KeywordServiceTest(final KeywordService keywordService,
                              final SessionRepository sessionRepository,
                              final KeywordRepository keywordRepository,
                              final EntityManager em) {
        this.keywordService = keywordService;
        this.sessionRepository = sessionRepository;
        this.keywordRepository = keywordRepository;
        this.em = em;
    }

    @Test
    void 최상위_키워드를_생성할_수_있다() {
        // given
        createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        KeywordCreateRequest createRequest = new KeywordCreateRequest("자바", "자바에 대한 설명", 1, 1, null);

        // when
        Long extract = keywordService.createKeyword(1L, createRequest);

        // then
        assertThat(extract).isNotNull();
    }

    @Test
    void 자식_키워드를_생성할_수_있다() {
        // given
        createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        createKeyword(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        KeywordCreateRequest createRequest = new KeywordCreateRequest("List", "List에 대한 설명", 1, 1, 1L);

        // when
        Long extract = keywordService.createKeyword(1L, createRequest);

        // then
        assertThat(extract).isNotNull();
    }

    private void createKeyword(final Keyword keyword) {
        keywordRepository.save(keyword);
        em.clear();
    }

    private void createAndSaveSession(final Session session) {
        sessionRepository.save(session);
        em.clear();
    }
}