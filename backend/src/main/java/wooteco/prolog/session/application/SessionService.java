package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.common.exception.BadRequestCode.DUPLICATE_SESSION_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_SESSION_NOT_FOUND_EXCEPTION;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.repository.MemberRepository;
import wooteco.prolog.organization.application.OrganizationService;
import wooteco.prolog.organization.domain.OrganizationGroupSession;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

    private final SessionMemberService sessionMemberService;
    private final OrganizationService organizationService;
    private final SessionRepository sessionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SessionResponse create(SessionRequest sessionRequest) {
        validateName(sessionRequest.getName());

        Session save = sessionRepository.save(sessionRequest.toEntity());
        return SessionResponse.of(save);
    }

    private void validateName(String name) {
        if (sessionRepository.findByName(name).isPresent()) {
            throw new BadRequestException(DUPLICATE_SESSION_EXCEPTION);
        }
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(ROADMAP_SESSION_NOT_FOUND_EXCEPTION));
    }

    public Optional<Session> findSessionById(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return sessionRepository.findById(id);
    }

    public List<SessionResponse> findAll() {
        return sessionRepository.findAll().stream()
            .map(SessionResponse::of)
            .collect(toList());
    }

    public List<SessionResponse> findAllOrderByDesc() {
        return sessionRepository.findAll().stream()
            .map(SessionResponse::of)
            .sorted((s1, s2) -> Long.compare(s2.getId(), s1.getId()))
            .collect(toList());
    }

    public List<SessionResponse> findMySessions(LoginMember loginMember) {
        Member member = memberRepository.findById(loginMember.getId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        List<OrganizationGroupSession> organizationGroupSessions = organizationService.findOrganizationGroupSessionsByMemberUsername(
            member.getUsername());
        List<SessionResponse> organizationSessions = organizationGroupSessions.stream()
            .map(it -> SessionResponse.of(it.getSession()))
            .collect(Collectors.toList());

        List<SessionResponse> mySessionResponses = findMySessionOnlyMine(loginMember);
        mySessionResponses.removeAll(organizationSessions);

        return Stream.of(mySessionResponses, organizationSessions)
            .flatMap(Collection::stream)
            .sorted((s1, s2) -> Long.compare(s2.getId(), s1.getId()))
            .collect(toList());
    }

    public List<Long> findMySessionIds(LoginMember loginMember) {
        return findMySessions(loginMember).stream().map(SessionResponse::getId).collect(toList());
    }

    public List<SessionResponse> findMySessionOnlyMine(LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            return new ArrayList<>();
        }
        List<Long> mySessionIds = findMySessionIds(loginMember.getId());

        return sessionRepository.findAllByIdInOrderByIdDesc(mySessionIds).stream()
            .map(SessionResponse::of)
            .collect(toList());
    }

    private List<Long> findMySessionIds(Long memberId) {
        return sessionMemberService.findByMemberId(memberId)
            .stream()
            .map(SessionMember::getSessionId)
            .collect(toList());
    }
}
