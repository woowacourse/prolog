package wooteco.prolog.roadmap.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
import wooteco.prolog.roadmap.application.dto.SessionsResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.support.utils.NewIntegrationTest;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@NewIntegrationTest
class NewSessionServiceTest {

    private final NewSessionService sessionService;
    private final SessionRepository sessionRepository;

    public NewSessionServiceTest(final NewSessionService sessionService, final SessionRepository sessionRepository) {
        this.sessionService = sessionService;
        this.sessionRepository = sessionRepository;
    }

    @Test
    void 세션을_생성한다() {
        Long actual = sessionService.createSession(1L, new SessionRequest("백엔드 레벨1"));

        assertThat(sessionRepository.findById(actual)).isPresent();
    }

    @Test
    void 세션_목록을_커리쿨럼_별로_조회할_수_있다() {
        sessionRepository.save(new Session(1L, "백엔드 레벨1"));
        sessionRepository.save(new Session(1L, "백엔드 레벨2"));
        sessionRepository.save(new Session(2L, "프론트엔드 레벨1"));

        SessionsResponse actual = sessionService.findSessions(1L);

        assertThat(actual.getSessions().size()).isEqualTo(2);
    }
}
