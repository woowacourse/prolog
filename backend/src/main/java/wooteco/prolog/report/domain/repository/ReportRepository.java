package wooteco.prolog.report.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
