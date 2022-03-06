package wooteco.prolog.studylog.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Studylog;

public interface StudylogRepository extends JpaRepository<Studylog, Long>,
    JpaSpecificationExecutor<Studylog> {

    List<Studylog> findByIdIn(List<Long> ids);

    Page<Studylog> findByIdInOrderByIdAsc(List<Long> ids, Pageable pageable);

    @Query(value = "select distinct p from Studylog p left join fetch p.studylogTags.values pt left join fetch pt.tag where p.member = :member",
        countQuery = "select count(p) from Studylog p where p.member = :member")
    Page<Studylog> findByMember(Member member, Pageable pageable);

    @Query(value = "select p from Studylog p where p.member = :member and p.createdAt between :start and :end")
    List<Studylog> findByMemberBetween(Member member, LocalDateTime start, LocalDateTime end);

    @Query("select count(p) from Studylog p where p.member = :member")
    int countByMember(Member member);

    List<Studylog> findAllByIdInOrderByIdDesc(List<Long> ids);

    @Query("select p from Studylog p where :date <= p.createdAt")
    List<Studylog> findByPastDays(LocalDateTime date);
}
