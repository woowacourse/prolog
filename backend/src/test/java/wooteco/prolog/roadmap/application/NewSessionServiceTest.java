package wooteco.prolog.roadmap.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.NotFoundErrorCodeException;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
import wooteco.prolog.roadmap.application.dto.SessionResponse;
import wooteco.prolog.roadmap.application.dto.SessionsResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.exception.SessionNotFoundException;

@ExtendWith(MockitoExtension.class)
class NewSessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private NewSessionService newSessionService;

    @Test
    @DisplayName("세션을 생성한다.")
    void createSession() {
        //given
        final Long 커리큘럼_아이디 = 1L;
        final SessionRequest 세션_요청_정보 = new SessionRequest("세션 1");
        final Session 세션 = new Session(1L, 1L, "세션 2");
        when(sessionRepository.save(any()))
            .thenReturn(세션);

        //when
        //then
        Assertions.assertThat(newSessionService.createSession(커리큘럼_아이디, 세션_요청_정보))
            .isSameAs(1L);
    }

    @Test
    @DisplayName("커리큘럼에 해당하는 세션을 반환한다.")
    void findSessions() {
        //given
        final List<Session> 세션_정보들 = Arrays.asList(
            new Session("세션 1"),
            new Session("세션 2"));
        when(sessionRepository.findAllByCurriculumId(any()))
            .thenReturn(세션_정보들);

        //when
        final SessionsResponse 세션_응답 = newSessionService.findSessions(1L);

        //then
        assertThat(세션_응답.getSessions())
            .extracting(SessionResponse::getName)
            .containsExactly("세션 1", "세션 2");
    }

    @Test
    @DisplayName("세션 정보를 업데이트한다.")
    void updateSession() {
        //given
        final SessionRequest 세션_요청_정보 = new SessionRequest("세션 2");
        final Session 세션 = new Session("세션 1");
        when(sessionRepository.findById(any()))
            .thenReturn(Optional.of(세션));

        //when
        newSessionService.updateSession(1L, 세션_요청_정보);

        //then
        assertThat(세션.getName())
            .isEqualTo("세션 2");
    }

    @Test
    @DisplayName("세션 정보 업데이트 시 존재하는 세션이 없으면 예외가 발생한다.")
    void updateSession_fail() {
        //given
        final SessionRequest 세션_요청_정보 = new SessionRequest("세션 1");
        when(sessionRepository.findById(any()))
            .thenThrow(SessionNotFoundException.class);

        //when
        //then
        assertThatThrownBy(() -> newSessionService.updateSession(1L, 세션_요청_정보))
            .isInstanceOf(SessionNotFoundException.class);
    }

    @Test
    @DisplayName("세션 정보를 삭제한다.")
    void deleteSession() {
        //given
        when(sessionRepository.existsById(any()))
            .thenReturn(true);

        //when
        newSessionService.deleteSession(1L);

        //then
        verify(sessionRepository, times(1))
            .deleteById(1L);
    }

    // TODO 프로덕션 코드 오류 해결되면 SessionNotFoundException 던져야 함.
    @Test
    @DisplayName("세션 정보 삭제 시 존재하는 세션이 없으면 예외가 발생한다.")
    void deleteSession_fail() {
        //given
        when(sessionRepository.existsById(any()))
            .thenReturn(false);

        //when
        //then
        assertThatThrownBy(() -> newSessionService.deleteSession(1L))
            .isInstanceOf(NotFoundErrorCodeException.class);
    }
}
