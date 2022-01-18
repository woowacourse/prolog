package wooteco.prolog.report.domain.ablity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.ablity.History;

import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    Optional<History> findFirstByOrderByCreatedAtDesc();
}
