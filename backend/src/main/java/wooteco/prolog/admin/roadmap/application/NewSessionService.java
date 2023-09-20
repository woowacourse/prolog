package wooteco.prolog.admin.roadmap.application;

import static wooteco.prolog.common.exception.BadRequestCode.ROADMAP_SESSION_NOT_FOUND_EXCEPTION;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.admin.roadmap.application.dto.SessionRequest;
import wooteco.prolog.admin.roadmap.application.dto.SessionResponse;
import wooteco.prolog.admin.roadmap.application.dto.SessionsResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;

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
            .orElseThrow(() -> new BadRequestException(ROADMAP_SESSION_NOT_FOUND_EXCEPTION));

        session.update(request.getName());
    }

    @Transactional
    public void deleteSession(final Long sessionId) {
        validateExistSession(sessionId);
        sessionRepository.deleteById(sessionId);
    }

    private void validateExistSession(final Long sessionId) {
        if (!sessionRepository.existsById(sessionId)) {
            throw new BadRequestException(ROADMAP_SESSION_NOT_FOUND_EXCEPTION);
        }
    }
}
