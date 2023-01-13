package wooteco.prolog.roadmap.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.support.utils.RepositoryTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RepositoryTest
class KeywordRepositoryTest {

    private final KeywordRepository keywordRepository;
    private final EntityManager em;

    public KeywordRepositoryTest(final KeywordRepository keywordRepository,
                                 final EntityManager em) {
        this.keywordRepository = keywordRepository;
        this.em = em;
    }

    @Test
    void 부모_키워드와_1뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Keyword keywordParent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, keywordParent));
        createKeywordChildren(Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, 1L, keywordParent));
        Long keywordParentId = keywordParent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchById(keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getId()).isNotNull(),
            () -> assertThat(extract.getChildren()).hasSize(2));
    }

    @Test
    void 부모_키워드와_2뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));

        Keyword children1 = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));
        Keyword children2 = createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, 1L, parent));

        createKeywordChildren(
            Keyword.createKeyword("List.of()", "List.of()에 대한 설명", 1, 1, 1L, children1));
        createKeywordChildren(
            Keyword.createKeyword("Set.of()", "Set.of()에 대한 설명", 1, 1, 1L, children2));

        Long keywordParentId = parent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchById(keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getId()).isNotNull(),
            () -> assertThat(extract.getChildren()).hasSize(2),
            () -> assertThat(extract.getChildren().iterator().next()
                .getChildren()).hasSize(1)
        );
    }

    @Test
    void 최상위_키워드만_조회한다() {
        // given
        Long sessionId = 1L;
        Keyword keywordParent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, keywordParent));
        createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, keywordParent));
        Long keywordParentId = keywordParent.getId();
        em.clear();

        // when
        final List<Keyword> extractParentKeyword = keywordRepository.findBySessionIdAndParentIsNull(
            sessionId);

        // then
        Assertions.assertThat(extractParentKeyword.size()).isEqualTo(1);
    }

    private Keyword createKeywordParent(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private Keyword createKeywordChildren(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }
}
