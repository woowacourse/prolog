package wooteco.prolog.session.application;

import static java.util.stream.Collectors.toList;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.session.application.dto.SessionRequest;
import wooteco.prolog.session.application.dto.SessionResponse;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.session.domain.repository.SessionRepository;
import wooteco.prolog.session.exception.DuplicateSessionException;
import wooteco.prolog.session.exception.SessionNotFoundException;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

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

    public List<SessionResponse> findAll() {
        return sessionRepository.findAll().stream()
            .map(SessionResponse::of)
            .collect(toList());
    }
}
