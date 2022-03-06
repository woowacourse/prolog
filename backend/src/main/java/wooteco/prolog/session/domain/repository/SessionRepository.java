package wooteco.prolog.session.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByName(String name);
}
