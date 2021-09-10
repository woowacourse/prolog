package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportsByMember(Member member, Pageable pageable);
}
