package wooteco.prolog.report.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.ReportAbility;

public interface ReportAbilityRepository extends JpaRepository<ReportAbility, Long> {

    List<ReportAbility> findByReportIdIn(List<Long> reportIds);
}

