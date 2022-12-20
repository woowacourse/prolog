package wooteco.prolog.roadmap.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
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
}
