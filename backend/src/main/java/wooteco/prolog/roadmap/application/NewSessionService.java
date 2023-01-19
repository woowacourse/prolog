package wooteco.prolog.roadmap.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
import wooteco.prolog.roadmap.application.dto.SessionResponse;
import wooteco.prolog.roadmap.application.dto.SessionsResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.exception.SessionNotFoundException;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class NewSessionService {

    private final SessionRepository sessionRepository;

    @Transactional
    public Long createSession(final Long curriculumId, final SessionRequest request) {
        Session session = sessionRepository.save(new Session(curriculumId, request.getName()));
        return session.getId();
    }

    public SessionsResponse findSessions(final Long curriculumId) {
        List<Session> sessions = sessionRepository.findAllByCurriculumId(curriculumId);

        return new SessionsResponse(sessions.stream()
            .map(SessionResponse::createResponse)
            .collect(Collectors.toList()));
    }

    @Transactional
    public void updateSession(final Long sessionId, final SessionRequest request) {
        Session session = sessionRepository.findById(sessionId)
            .orElseThrow(SessionNotFoundException::new);

        session.update(request.getName());
    }

    @Transactional
    public void deleteSession(final Long sessionId) {
        validateExistSession(sessionId);
        sessionRepository.deleteById(sessionId);
    }

    private void validateExistSession(final Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new SessionNotFoundException();
        }
    }
}
