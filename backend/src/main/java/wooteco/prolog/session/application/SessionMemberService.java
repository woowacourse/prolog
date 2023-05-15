package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SessionMemberService {

    private SessionMemberRepository sessionMemberRepository;
    private SessionRepository sessionRepository;
    private MemberService memberService;
    private GroupMemberService groupMemberService;

    @Transactional
    public void registerMember(Long sessionId, Long memberId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException();
        }
        final Member member = memberService.findById(memberId);
        sessionMemberRepository.save(new SessionMember(sessionId, member));
    }

    @Transactional
    public void registerMembersByGroupId(Long sessionId, SessionGroupMemberRequest sessionGroupMemberRequest) {
        List<SessionMember> alreadySessionMembers = sessionMemberRepository.findAllBySessionId(sessionId);

        List<Member> members = groupMemberService.findGroupMemberByGroupId(sessionGroupMemberRequest.getGroupId()).stream()
            .map(it -> it.getMember())
            .collect(toList());

        List<SessionMember> sessionMembers = members.stream()
            .map(it -> new SessionMember(sessionId, it))
            .filter(it -> !alreadySessionMembers.contains(it))
            .collect(toList());

        sessionMemberRepository.saveAll(sessionMembers);
    }

    public List<MemberResponse> findAllMembersBySessionId(Long sessionId) {
        List<SessionMember> sessionMembers = sessionMemberRepository.findAllBySessionId(sessionId);

        return sessionMembers.stream()
            .map(it -> MemberResponse.of(it.getMember()))
            .collect(toList());
    }

    public List<SessionMember> findByMemberId(Long memberId) {
        Member member = memberService.findById(memberId);
        return sessionMemberRepository.findByMember(member);
    }

    @Transactional
    public void deleteRegistedSession(Long sessionId, Long memberId) {
        Member member = memberService.findById(memberId);
        SessionMember sessionMember = findSessionMemberBySessionIdAndMemberId(
            sessionId,
            member
        );

        sessionMemberRepository.delete(sessionMember);
    }

    private SessionMember findSessionMemberBySessionIdAndMemberId(Long sessionId, Member member) {
        return sessionMemberRepository.findBySessionIdAndMember(sessionId, member)
            .orElseThrow(SessionNotFoundException::new);
    }
}
