package wooteco.prolog.report.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findByMemberId(Long id, Pageable pageable);
}
