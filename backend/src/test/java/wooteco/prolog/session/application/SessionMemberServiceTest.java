package wooteco.prolog.session.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.common.exception.NotFoundErrorCodeException;
import wooteco.prolog.member.application.GroupMemberService;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.dto.SessionGroupMemberRequest;
import wooteco.prolog.session.application.dto.SessionMemberRequest;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.exception.SessionNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wooteco.prolog.member.domain.Role.CREW;

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
    private GroupMemberService groupMemberService;

    @DisplayName("Member가 회원가입을 할 수 있어야 한다.")
    @Test
    void registerMember() {
        // given
        final Member member = new Member("bebe", "베베", CREW, 1L, "img");
        when(sessionRepository.existsById(1L)).thenReturn(true);
        doReturn(member).when(memberService).findById(1L);

        // when
        sessionMemberService.registerMember(1L, 1L);

        // then
        verify(sessionRepository, times(1)).existsById(1L);
        verify(memberService, times(1)).findById(1L);
        verify(sessionMemberRepository, times(1))
            .save(new SessionMember(1L, member));
    }

    @DisplayName("SessionId가 이미 존재하는 Member는 회원가입을 할 수 없다.")
    @Test
    void registerMemberFail() {
        // given
        when(sessionRepository.existsById(1L)).thenReturn(false);

        // when, then
        Assertions.assertThrows(NotFoundErrorCodeException.class, () -> sessionMemberService.registerMember(1L, 1L));
    }

    @DisplayName("Member들이 회원가입을 할 수 있다.")
    @Test
    void registerMembers() {
        // given
        final List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);
        final SessionMemberRequest request = new SessionMemberRequest(ids);

        // when
        sessionMemberService.registerMembers(1L, request);

        // then
        verify(memberService, atMostOnce()).findByIdIn(request.getMemberIds());
        verify(sessionMemberRepository, atMostOnce()).saveAll(null);
    }

    @DisplayName("Members는 GroupId로 회원가입 할 수 있다.")
    @Test
    void registerMembersByGroupId() {
        // given
        final SessionGroupMemberRequest request = new SessionGroupMemberRequest(1L);

        // when
        sessionMemberService.registerMembersByGroupId(1L, request);

        // then
        verify(sessionMemberRepository, atMostOnce()).findAllBySessionId(1L);
        verify(groupMemberService, atMostOnce()).findGroupMemberByGroupId(request.getGroupId());
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

    @DisplayName("SesssionMember를 존재하지 않는 SesisonId와 MemberId로 조회시 예외 발생")
    @Test
    void deleteRegistedSessionFail() {
        // when, then
        Assertions.assertThrows(
            NotFoundErrorCodeException.class, () -> sessionMemberService.deleteRegistedSession(1L, 1L)
        );
    }

}
