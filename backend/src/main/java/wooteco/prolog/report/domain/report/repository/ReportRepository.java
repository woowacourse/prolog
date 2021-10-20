package wooteco.prolog.report.domain.report.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findReportsByMember(Member member, Pageable pageable);

    @Query(value = "select r from Report r where r.member.username = :username",
        countQuery = "select count(*) from Report r where r.member.username = :username")
    Page<Report> findReportsByUsername(String username, Pageable pageable);

    @Query(value = "select r from Report r where r.title = :title and r.member.username = :username")
    Optional<Report> findReportByTitleAndMemberUsername(String title, String username);

    @Query("select r from Report r where r.isRepresent = true and r.member.username = :username")
    Optional<Report> findRepresentReportOf(String username);
}
