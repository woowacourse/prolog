package wooteco.prolog.mission.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.mission.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByName(String name);
}
