package wooteco.prolog.report.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.ReportStudylog;

public interface ReportStudylogRepository extends JpaRepository<ReportStudylog, Long> {

    List<ReportStudylog> findByReportId(Long reportId);
}

