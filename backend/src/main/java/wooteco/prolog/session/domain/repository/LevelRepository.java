package wooteco.prolog.session.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByName(String name);
}
