package wooteco.prolog.report.domain.report.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.report.Report2;

public interface ReportRepository2 extends JpaRepository<Report2, Long> {

    Page<Report2> findReportsByMember(Member member, Pageable pageable);

    @Query(value = "select r from Report2 r where r.member.username = :username",
        countQuery = "select count(*) from Report2 r where r.member.username = :username")
    Page<Report2> findReportsByUsername(String username, Pageable pageable);

    @Query(value = "select r from Report2 r where r.title = :title and r.member.username = :username")
    Optional<Report2> findReportByTitleAndMemberUsername(String title, String username);

    @Query("select r from Report2 r where r.isRepresent = true and r.member.username = :username")
    Optional<Report2> findRepresentReportOf(String username);
}

