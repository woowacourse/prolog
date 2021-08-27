package wooteco.prolog.level.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.level.domain.Level;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Optional<Level> findByName(String name);
}
