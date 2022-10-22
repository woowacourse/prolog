package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.Keyword;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.exception.KeywordAndKeywordParentSameException;
import wooteco.prolog.roadmap.exception.KeywordNotFoundException;
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

        em.clear();

        // when
        Long extract = keywordService.createKeyword(1L, createRequest);

        // then
        assertThat(extract).isNotNull();
    }

    @Test
    void 자식_키워드를_생성할_수_있다() {
        // given
        createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        KeywordCreateRequest createRequest = new KeywordCreateRequest("List", "List에 대한 설명", 1, 1, 1L);

        em.clear();

        // when
        Long extract = keywordService.createKeyword(1L, createRequest);

        // then
        assertThat(extract).isNotNull();
    }

    @Test
    void 키워드를_조회할_수_있다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword keyword = createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));

        Long sessionId = session.getId();
        Long keywordId = keyword.getId();
        em.clear();

        // when
        KeywordResponse extract = keywordService.findKeyword(sessionId, keywordId);

        // then
        assertThat(extract.getKeywordId()).isEqualTo(1);
    }

    @Test
    void 키워드를_조회할때_값이_없는경우_예외가_발생한다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Long sessionId = session.getId();

        em.clear();

        // when & then
        assertThatThrownBy(() -> keywordService.findKeyword(sessionId, 1L))
            .isInstanceOf(KeywordNotFoundException.class);
    }

    @Test
    void 부모_키워드를_조회하면_소속된_자식_키워드도_함께_조회된다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));

        Keyword children1 = createKeywordChildren(Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));
        Keyword children2 = createKeywordChildren(Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, 1L, parent));

        createKeywordChildren(Keyword.createKeyword("List.of()", "List.of()에 대한 설명", 1, 1, 1L, children1));
        createKeywordChildren(Keyword.createKeyword("Set.of()", "Set.of()에 대한 설명", 1, 1, 1L, children2));

        Long sessionId = session.getId();
        Long keywordParentId = parent.getId();
        em.clear();

        // when
        KeywordResponse extract = keywordService.findKeywordWithAllChild(sessionId, keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getChildrenKeywords()).hasSize(2),
            () -> assertThat(extract.getChildrenKeywords().get(0).getChildrenKeywords()).hasSize(1),
            () -> assertThat(extract.getChildrenKeywords().get(1).getChildrenKeywords()).hasSize(1)
        );
    }

    @Test
    void 세션에_속한_최상위_키워드들을_조회할_수_있다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        createKeywordParent(Keyword.createKeyword("스프링", "스프링에 대한 설명", 2, 1, 1L, null));

        Long sessionId = session.getId();

        em.clear();

        // when
        KeywordsResponse extract = keywordService.findSessionIncludeRootKeywords(sessionId);

        // then
        assertThat(extract.getData()).hasSize(2);
    }

    @Test
    void 키워드의_내용을_수정할_수_있다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword keyword = createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));

        Long sessionId = session.getId();
        Long keywordId = keyword.getId();
        em.clear();

        // when
        KeywordUpdateRequest keywordUpdateRequest = new KeywordUpdateRequest("자바스크립트", "자바스크립트에 대한 설명", 1, 2, null);
        keywordService.updateKeyword(sessionId, keywordId, keywordUpdateRequest);
        em.clear();

        // then
        Keyword extract = keywordRepository.findById(keywordId).get();
        assertThat(extract.getName()).isEqualTo("자바스크립트");
    }

    @Test
    void 키워드의_ID가_부모의_ID와_같은경우_예외가_발생한다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        Keyword child = createKeywordChildren(Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));

        Long sessionId = session.getId();
        Long keywordChildId = child.getId();
        em.clear();

        // when & then
        KeywordUpdateRequest keywordUpdateRequest = new KeywordUpdateRequest("자바스크립트", "자바스크립트에 대한 설명", 1, 2, keywordChildId);
        assertThatThrownBy(() -> keywordService.updateKeyword(sessionId, keywordChildId, keywordUpdateRequest))
            .isInstanceOf(KeywordAndKeywordParentSameException.class);
    }

    @Test
    void 키워드를_삭제할_수_있다() {
        // given
        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        Keyword child = createKeywordChildren(Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));

        Long sessionId = session.getId();
        Long keywordParentId = parent.getId();
        Long keywordChildId = child.getId();
        em.clear();

        // when
        keywordService.deleteKeyword(sessionId, keywordParentId);
        keywordRepository.flush();
        em.clear();

        // then
        Optional<Keyword> extract = keywordRepository.findById(keywordChildId);
        assertThat(extract).isNotPresent();
    }

    private Keyword createKeywordParent(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private Keyword createKeywordChildren(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private Session createAndSaveSession(final Session session) {
        return sessionRepository.save(session);
    }
}