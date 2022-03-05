package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.application.dto.MemberResponse;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.application.dto.SessionMemberRequest;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionMemberRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SessionMemberService {

    private SessionMemberRepository sessionMemberRepository;
    private MemberService memberService;

    @Transactional
    public void registerMembers(Long sessionId, SessionMemberRequest sessionMemberRequest) {
        List<Member> members = memberService.findByIdIn(sessionMemberRequest.getMemberIds());
        members.stream()
            .map(it -> new SessionMember(sessionId, it))
            .collect(toList());
    }

    public List<MemberResponse> findAllMembersBySessionId(Long sessionId) {
        List<SessionMember> sessionMembers = sessionMemberRepository.findAllBySessionId(sessionId);

        return sessionMembers.stream()
            .map(it -> MemberResponse.of(it.getMember()))
            .collect(toList());
    }
}
