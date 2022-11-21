package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.KeywordCreateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordResponse;
import wooteco.prolog.roadmap.application.dto.KeywordUpdateRequest;
import wooteco.prolog.roadmap.application.dto.KeywordsResponse;
import wooteco.prolog.roadmap.domain.Keyword;
import wooteco.prolog.roadmap.domain.repository.KeywordRepository;
import wooteco.prolog.roadmap.exception.KeywordAndKeywordParentSameException;
import wooteco.prolog.roadmap.exception.KeywordNotFoundException;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.NewIntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@NewIntegrationTest
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

    @Nested
    class 키워드_생성_요청_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        KeywordCreateRequest createRequest = new KeywordCreateRequest("자바", "자바에 대한 설명", 1, 1,
            null);

        @Nested
        class 부모_값에_null이_포함된_키워드를_생성하면 {

            Long extract = keywordService.createKeyword(session.getId(), createRequest);

            @Test
            void 최상위_키워드를_생성한다() {
                assertThat(extract).isNotNull();
            }
        }

        @Nested
        class 부모_값에_다른_키워드_값이_포함되면 {

            Keyword keyword = createKeywordParent(
                Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session.getId(), null));

            KeywordCreateRequest createRequest = new KeywordCreateRequest("List", "List에 대한 설명", 1,
                1, keyword.getId());
            Long extract = keywordService.createKeyword(session.getId(), createRequest);

            @Test
            void 자식_키워드를_생성한다() {
                assertThat(extract).isNotNull();
            }
        }
    }

    @Nested
    class 단일_키워드_조회_요청_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword keyword = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session.getId(), null));

        @Nested
        class 세션_ID와_키워드_ID를_통해 {

            KeywordResponse extract = keywordService.findKeyword(session.getId(), keyword.getId());

            @Test
            void 단일_키워드를_조회할_수_있다() {
                assertThat(extract.getKeywordId()).isEqualTo(1);
            }
        }

        @Test
        void 존재하지_않는_키워드로_조회하면_예외가_발생한다() {
            assertThatThrownBy(() -> keywordService.findKeyword(
                createAndSaveSession(new Session("2022 백엔드 레벨 1")).getId(), 999L))
                .isInstanceOf(KeywordNotFoundException.class);
        }
    }

    @Nested
    class 단일_키워드_연관_조회_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session.getId(), null));

        Keyword child_one = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, session.getId(), parent));
        Keyword child_two = createKeywordChildren(
            Keyword.createKeyword("Set", "Set에 대한 설명", 2, 1, session.getId(), parent));

        Keyword child_one_child = createKeywordChildren(
            Keyword.createKeyword("List.of()", "List.of()에 대한 설명", 1, 1, session.getId(),
                child_one));
        Keyword child_two_child = createKeywordChildren(
            Keyword.createKeyword("Set.of()", "Set.of()에 대한 설명", 1, 1, session.getId(), child_two));

        @Nested
        class 세션_ID와_키워드_ID를_통해 {

            KeywordResponse extract = keywordService.findKeywordWithAllChild(session.getId(),
                parent.getId());

            @Test
            void 조회한_키워드의_자식_키워드들까지_함께_조회할_수_있다() {
                assertAll(
                    () -> assertThat(extract.getChildrenKeywords()).hasSize(2),
                    () -> assertThat(extract.getChildrenKeywords().iterator().next()
                        .getChildrenKeywords()).hasSize(1)
                );
            }
        }
    }

    @Nested
    class 세션_연관_키워드_조회_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword keyword_one = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session.getId(), null));
        Keyword keyword_two = createKeywordParent(
            Keyword.createKeyword("스프링", "스프링에 대한 설명", 2, 1, session.getId(), null));

        @Nested
        class 세션_ID를_통해서 {

            KeywordsResponse extract = keywordService.findSessionIncludeRootKeywords(
                session.getId());

            @Test
            void 최상위_키워드들을_조회할_수_있다() {
                assertThat(extract.getData()).hasSize(2);
            }
        }
    }

    @Nested
    class 키워드_수정_요청_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, session.getId(), null));

        @Test
        void 내용을_수정할_수_있다() {
            KeywordUpdateRequest keywordUpdateRequest = new KeywordUpdateRequest("자바스크립트",
                "자바스크립트에 대한 설명", 1, 2, null);
            keywordService.updateKeyword(session.getId(), parent.getId(), keywordUpdateRequest);
            keywordRepository.flush();
            em.clear();

            Keyword extract = keywordRepository.findById(parent.getId()).get();
            assertThat(extract.getName()).isEqualTo("자바스크립트");
        }

        @Nested
        class 수정한_키워드의_ID가_부모의_ID와_같은경우 {

            Keyword child = createKeywordChildren(
                Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));
            KeywordUpdateRequest request = new KeywordUpdateRequest("자바스크립트", "자바스크립트에 대한 설명", 1, 2,
                child.getId());

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(
                    () -> keywordService.updateKeyword(session.getId(), child.getId(), request))
                    .isInstanceOf(KeywordAndKeywordParentSameException.class);
            }
        }
    }

    @Nested
    class 키워드_삭제_요청_시 {

        Session session = createAndSaveSession(new Session("2022 백엔드 레벨 1"));
        Keyword parent = createKeywordParent(
            Keyword.createKeyword("자바", "자바에 대한 설명", 1, 1, 1L, null));
        Keyword child = createKeywordChildren(
            Keyword.createKeyword("List", "List에 대한 설명", 1, 1, 1L, parent));

        @Test
        void 키워드를_삭제할_수_있다() {
            keywordService.deleteKeyword(session.getId(), parent.getId());
            keywordRepository.flush();
            em.clear();

            Optional<Keyword> extractParent = keywordRepository.findById(parent.getId());
            Optional<Keyword> extractChild = keywordRepository.findById(child.getId());
            assertAll(
                () -> assertThat(extractParent).isNotPresent(),
                () -> assertThat(extractChild).isNotPresent()
            );
        }
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
