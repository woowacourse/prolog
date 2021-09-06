package wooteco.prolog.studylog.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.Studylog;

public interface StudylogRepository extends JpaRepository<Studylog, Long>, JpaSpecificationExecutor<Studylog> {

    Page<Studylog> findByMember(Member member, Pageable pageable);

    Page<Studylog> findByIdIn(List<Long> ids, Pageable pageable);
}
