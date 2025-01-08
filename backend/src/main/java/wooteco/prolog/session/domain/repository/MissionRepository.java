package wooteco.prolog.session.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.Mission;

import java.util.List;
import java.util.Optional;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Optional<Mission> findByName(String name);

    List<Mission> findBySessionIdIn(List<Long> sessionIds);
}
