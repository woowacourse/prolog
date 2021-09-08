package wooteco.prolog.studylog.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.Level;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByName(String name);
}
