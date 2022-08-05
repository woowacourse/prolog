package kr.co.techcourse.prolog.batch.job.sample.tasklet.east.repository;

import kr.co.techcourse.prolog.batch.job.sample.tasklet.east.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
