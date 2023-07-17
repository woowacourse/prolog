package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;
import static wooteco.prolog.common.exception.BadRequestCode.DUPLICATE_SESSION_EXCEPTION;
import static wooteco.prolog.common.exception.BadRequestCode.SESSION_NOT_FOUND_EXCEPTION;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.SessionMember;
import wooteco.prolog.session.domain.repository.SessionRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

    private SessionMemberService sessionMemberService;
    private SessionRepository sessionRepository;

    @Transactional
    public SessionResponse create(SessionRequest sessionRequest) {
        validateName(sessionRequest.getName());

        Session save = sessionRepository.save(sessionRequest.toEntity());
        return SessionResponse.of(save);
    }

    private void validateName(String name) {
        if (sessionRepository.findByName(name).isPresent()) {
            throw new DuplicateSessionException();
        }
    }

    public Session findById(Long id) {
        return sessionRepository.findById(id)
            .orElseThrow(SessionNotFoundException::new);
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

    public List<SessionResponse> findMySessions(LoginMember member) {
        List<Long> sessionIds = findMySessionIds(member.getId());

        return sessionRepository.findAllById(sessionIds).stream()
            .map(SessionResponse::of)
            .collect(toList());
    }

    public List<Long> findMySessionIds(Long memberId) {
        return sessionMemberService.findByMemberId(memberId)
            .stream()
            .map(SessionMember::getSessionId)
            .collect(toList());
    }

    public List<SessionResponse> findAllWithMySessionFirst(LoginMember loginMember) {
        if (loginMember.isAnonymous()) {
            return findAll();
        }

        List<SessionResponse> mySessions = findMySessions(loginMember);
        List<SessionResponse> allSessions = findAll();
        allSessions.removeAll(mySessions);

        return Stream.of(mySessions, allSessions)
            .flatMap(Collection::stream)
            .collect(toList());
    }
}
