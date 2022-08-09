package wooteco.prolog.levellogs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.levellogs.domain.LevelLog;

public interface LevelLogRepository extends JpaRepository<LevelLog, Long> {

}
