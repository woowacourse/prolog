package wooteco.prolog.session.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.NotFoundErrorCodeException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionMemberService sessionMemberService;

    @Mock
    private SessionRepository sessionRepository;

    @DisplayName("Session을 생성한다.")
    @Test
    void create() {
        // given
        final SessionRequest request = new SessionRequest("session");
        doReturn(new Session("session result")).when(sessionRepository).save(request.toEntity());

        // when
        final SessionResponse response = sessionService.create(request);

        // then
        assertThat(response.getName()).isEqualTo("session result");
    }

    @DisplayName("Session을 생성할 때 예외가 발생한다.")
    @Test
    void createFail() {
        // given
        final SessionRequest request = new SessionRequest("session");
        final Optional<Session> session = Optional.of(new Session("session"));
        doReturn(session)
            .when(sessionRepository).findByName(request.getName());

        // when, then
        assertThrows(NotFoundErrorCodeException.class, () -> sessionService.create(request));
    }

    @DisplayName("Id로 Session을 조회한다.")
    @Test
    void findById() {
        // given
        doReturn(Optional.of(new Session("session"))).when(sessionRepository).findById(1L);

        // when
        final Session session = sessionService.findById(1L);

        // then
        assertThat(session.getName()).isEqualTo("session");
    }

    @DisplayName("Id로 Session을 조회할 때 예외가 발생한다.")
    @Test
    void findByIdFail() {
        // when, then
        assertThrows(NotFoundErrorCodeException.class, () -> sessionService.findById(1L));
    }

    @DisplayName("Id로 Optional<Session>을 조회한다.")
    @Test
    void findSessionById() {
        // given
        doReturn(Optional.of(new Session("session"))).when(sessionRepository).findById(1L);

        // when, then
        assertThat(sessionService.findSessionById(1L).get().getName()).isEqualTo("session");
    }

    @DisplayName("Id로 Optional<Session>을 조회할 때 Optional<empty>가 반환된다.")
    @Test
    void findSessionByIdFail() {
        // when, then
        assertThat(sessionService.findSessionById(null).isPresent()).isFalse();
    }

    @DisplayName("모든 Session을 조회한다.")
    @Test
    void findAll() {
        // given
        final List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("session1"));
        sessions.add(new Session("session2"));
        sessions.add(new Session("session3"));

        doReturn(sessions).when(sessionRepository).findAll();

        // when
        List<SessionResponse> responses = sessionService.findAll();

        // then
        assertAll(
            () -> assertThat(responses).extracting(SessionResponse::getName)
                .containsExactly("session1", "session2", "session3")
        );
    }

    @DisplayName("현재 로그인한 Memeber의 Session을 조회한다.")
    @Test
    void findMySessions() {
        // given
        final LoginMember member = new LoginMember(1L, LoginMember.Authority.MEMBER);
        final List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("session1"));

        final List<SessionMember> sessionMembers = new ArrayList<>();
        sessionMembers.add(new SessionMember(1L, new Member("member1", "베베", Role.CREW, Long.MIN_VALUE, "img")));

        doReturn(sessionMembers).when(sessionMemberService).findByMemberId(member.getId());
        doReturn(sessions).when(sessionRepository).findAllById(Arrays.asList(1L));

        // when
        List<SessionResponse> responses = sessionService.findMySessions(member);

        // then
        assertThat(responses.get(0).getName()).isEqualTo("session1");
    }

    @DisplayName("현재 로그인한 Member의 SessionId들을 조회한다.")
    @Test
    void findMySessionIds() {
        // given
        final LoginMember member = new LoginMember(1L, LoginMember.Authority.MEMBER);
        final List<SessionMember> sessionMembers = new ArrayList<>();
        sessionMembers.add(new SessionMember(1L, new Member("member1", "베베", Role.CREW, Long.MIN_VALUE, "img")));

        doReturn(sessionMembers).when(sessionMemberService).findByMemberId(member.getId());

        // when
        List<Long> sessionIds = sessionService.findMySessionIds(member.getId());

        // then
        assertAll(
            () -> assertThat(sessionIds.get(0)).isEqualTo(1L),
            () -> assertThat(sessionIds.size()).isEqualTo(1)
        );
    }

}
