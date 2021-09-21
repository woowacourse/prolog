package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findReportsByMember(Member member, Pageable pageable);

    @Query("select r from Report r where r.member.username = :username")
    List<Report> findReportsByUsername(String username, Pageable pageable);
}
