package wooteco.prolog.roadmap.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.roadmap.application.dto.SessionRequest;
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
}
