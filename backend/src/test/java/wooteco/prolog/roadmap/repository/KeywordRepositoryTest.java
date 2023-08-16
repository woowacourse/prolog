package wooteco.prolog.roadmap.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.roadmap.domain.Curriculum;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.CurriculumRepository;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private CurriculumRepository curriculumRepository;

    @Test
    void 부모_키워드와_1뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Session session = new Session("테스트 세션");
        sessionRepository.save(session);
        Long sessionId = session.getId();

        Keyword keywordParent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));
        createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, keywordParent));
        createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, keywordParent));
        Long keywordParentId = keywordParent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchByIdOrderBySeq(keywordParentId);

        // then
        assertAll(
            () -> assertThat(extract.getId()).isNotNull(),
            () -> assertThat(extract.getChildren()).hasSize(2));
    }

    @Test
    void 부모_키워드와_2뎁스까지의_자식_키워드를_함께_조회할_수_있다() {
        // given
        Session session = new Session("테스트 세션");
        sessionRepository.save(session);
        Long sessionId = session.getId();

        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, sessionId, null));

        Keyword children1 = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, sessionId, parent));
        Keyword children2 = createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, sessionId, parent));

        createKeywordChildren(
            Keyword.createKeyword("List.of()", "List.of()에 대한 설명", 1, 1, sessionId, children1));
        createKeywordChildren(
            Keyword.createKeyword("Set.of()", "Set.of()에 대한 설명", 1, 1, sessionId, children2));

        Long keywordParentId = parent.getId();
        em.clear();

        // when
        Keyword extract = keywordRepository.findFetchByIdOrderBySeq(keywordParentId);

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
        Session session = new Session("테스트 세션");
        sessionRepository.save(session);
        Long sessionId = session.getId();

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
        assertThat(extractParentKeyword.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("세션 ID 리스트로 키워드 리스트를 조회한다")
    void findBySessionIdIn() {
        //given
        final Curriculum curriculum = curriculumRepository.save(new Curriculum("커리큘럼1"));

        final Session session1 = sessionRepository.save(new Session(curriculum.getId(), "세션1"));
        final Session session2 = sessionRepository.save(new Session(curriculum.getId(), "세션2"));
        final Session session3 = sessionRepository.save(new Session(curriculum.getId(), "세션3"));
        final Session session4 = sessionRepository.save(new Session(curriculum.getId(), "세션4"));
        final Session session5 = sessionRepository.save(new Session(curriculum.getId(), "세션5"));

        final Keyword keyword1 = keywordRepository.save(
            Keyword.createKeyword("자바1", "자바 설명1", 1, 5, session1.getId(), null));
        final Keyword keyword2 = keywordRepository.save(
            Keyword.createKeyword("자바2", "자바 설명2", 2, 5, session1.getId(), keyword1));
        final Keyword keyword3 = keywordRepository.save(
            Keyword.createKeyword("자바3", "자바 설명3", 3, 5, session1.getId(), null));
        final Keyword keyword4 = keywordRepository.save(
            Keyword.createKeyword("자바4", "자바 설명4", 4, 5, session1.getId(), keyword3));
        keywordRepository.save(
            Keyword.createKeyword("자바5", "자바 설명5", 5, 5, session2.getId(), null));
        keywordRepository.save(
            Keyword.createKeyword("자바6", "자바 설명6", 6, 5, session2.getId(), keyword1));
        keywordRepository.save(
            Keyword.createKeyword("자바7", "자바 설명7", 7, 5, session2.getId(), null));
        keywordRepository.save(
            Keyword.createKeyword("자바8", "자바 설명8", 8, 5, session3.getId(), keyword2));
        final Keyword keyword9 = keywordRepository.save(
            Keyword.createKeyword("자바9", "자바 설명9", 9, 5, session4.getId(), keyword2));
        final Keyword keyword10 = keywordRepository.save(
            Keyword.createKeyword("자바10", "자바 설명10", 10, 5, session5.getId(), null));

        final HashSet<Long> sessionIds = new HashSet<>(
            Arrays.asList(session1.getId(), session4.getId(), session5.getId())
        );

        //when
        final List<Keyword> keywords = keywordRepository.findBySessionIdIn(sessionIds);

        //then
        assertThat(keywords)
            .usingRecursiveComparison()
            .ignoringFields("id", "parent.id")
            .isEqualTo(Arrays.asList(keyword1, keyword2, keyword3, keyword4, keyword9, keyword10));
    }

    private Keyword createKeywordParent(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    private Keyword createKeywordChildren(final Keyword keyword) {
        return keywordRepository.save(keyword);
    }
}
