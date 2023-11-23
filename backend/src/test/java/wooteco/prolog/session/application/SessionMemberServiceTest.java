package wooteco.prolog.session.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.member.domain.Role.CREW;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.application.DepartmentMemberService;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.dto.SessionDepartmentMemberRequest;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;

@ExtendWith(MockitoExtension.class)
class SessionMemberServiceTest {

    @InjectMocks
    private SessionMemberService sessionMemberService;

    @Mock
    private SessionMemberRepository sessionMemberRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private DepartmentMemberService departmentMemberService;

    @DisplayName("Member가 회원가입을 할 수 있어야 한다.")
    @Test
    void registerMember() {
        // given
        final Long sessionId = 1L;
        final Long memberId = 1L;
        final Member member = new Member("bebe", "베베", CREW, 1L, "img");
        when(sessionRepository.existsById(sessionId)).thenReturn(true);
        doReturn(member).when(memberService).findById(memberId);

        // when
        sessionMemberService.registerMember(sessionId, memberId);

        // then
        verify(sessionRepository, times(1)).existsById(sessionId);
        verify(memberService, times(1)).findById(memberId);
        verify(sessionMemberRepository, times(1))
            .save(new SessionMember(sessionId, member));
    }

    @DisplayName("SessionId가 이미 존재하는 Member는 회원가입을 할 수 없다.")
    @Test
    void registerMemberFail() {
        // given
        when(sessionRepository.existsById(1L)).thenReturn(false);

        // when, then
        assertThatThrownBy(() -> sessionMemberService.registerMember(1L, 1L))
            .isInstanceOf(BadRequestException.class);
    }

    @DisplayName("Members는 GroupId로 회원가입 할 수 있다.")
    @Test
    void registerMembersByGroupId() {
        // given
        final SessionDepartmentMemberRequest request = new SessionDepartmentMemberRequest(1L);

        // when
        sessionMemberService.registerMembersByGroupId(1L, request);

        // then
        verify(sessionMemberRepository, atMostOnce()).findAllBySessionId(1L);
        verify(departmentMemberService, atMostOnce()).findDepartmentMemberByDepartmentId(request.getDepartmentId());
        verify(sessionMemberRepository, atMostOnce()).saveAll(null);

    }

    @DisplayName("모든 Member들을 SessionId로 조회한다.")
    @Test
    void findAllMembersBySessionId() {
        // given
        final List<SessionMember> sessionMembers = new ArrayList<>();
        sessionMembers.add(new SessionMember(1L, new Member("bebe1", "베베1", CREW, 1L, "img")));
        sessionMembers.add(new SessionMember(2L, new Member("bebe2", "베베2", CREW, 1L, "img")));
        sessionMembers.add(new SessionMember(3L, new Member("hoy", "호이", CREW, 1L, "img")));

        doReturn(sessionMembers).when(sessionMemberRepository).findAllBySessionId(1L);

        // when
        final List<MemberResponse> responses = sessionMemberService.findAllMembersBySessionId(1L);

        // then
        assertAll(
            () -> assertThat(responses).extracting(MemberResponse::getNickname)
                .contains("베베1", "베베2", "호이"),
            () -> assertThat(responses).extracting(MemberResponse::getUsername)
                .contains("bebe1", "bebe2", "hoy")
        );
    }

    @DisplayName("List<SessionMember>를 MemberId로 조회한다.")
    @Test
    void findByMemberId() {
        // given
        final Member member = new Member("bebe", "베베", CREW, 1L, "img");
        doReturn(member).when(memberService).findById(1L);

        final List<SessionMember> sessionMembers = new ArrayList<>();
        sessionMembers.add(new SessionMember(1L, new Member("bebe1", "베베1", CREW, 1L, "img")));
        sessionMembers.add(new SessionMember(2L, new Member("bebe2", "베베2", CREW, 1L, "img")));
        sessionMembers.add(new SessionMember(3L, new Member("hoy", "호이", CREW, 1L, "img")));
        doReturn(sessionMembers).when(sessionMemberRepository).findByMember(member);

        // when
        List<SessionMember> results = sessionMemberService.findByMemberId(1L);

        // then
        assertThat(results).extracting(SessionMember::getMember).contains(member);
    }

    @DisplayName("RegistedSession 삭제 테스트")
    @Test
    void deleteRegistedSession() {
        // given
        final Long sessionId = 1L;
        final Long memberId = 1L;
        Member member = new Member(memberId, "userName", "user", CREW, 1L, "imageUrl");
        SessionMember sessionMember = new SessionMember(sessionId, member);

        doReturn(member).when(memberService).findById(memberId);
        doReturn(Optional.of(sessionMember)).when(sessionMemberRepository)
            .findBySessionIdAndMember(sessionId, member);
        // when
        sessionMemberService.deleteRegistedSession(sessionId, memberId);

        // then
        verify(sessionMemberRepository, atMostOnce()).delete(sessionMember);
    }

    @DisplayName("SesssionMember를 존재하지 않는 SesisonId와 MemberId로 조회시 예외 발생")
    @Test
    void deleteRegistedSessionFail() {
        // when, then
        assertThatThrownBy(() -> sessionMemberService.deleteRegistedSession(1L, 1L))
            .isInstanceOf(BadRequestException.class);
    }

}
