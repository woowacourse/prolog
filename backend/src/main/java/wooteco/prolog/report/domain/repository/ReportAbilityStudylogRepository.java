package wooteco.prolog.report.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.ReportAbilityStudylog;

public interface ReportAbilityStudylogRepository extends JpaRepository<ReportAbilityStudylog, Long> {

    List<ReportAbilityStudylog> findByReportAbilityIdIn(List<Long> reportAbilityStudylogIds);
}

