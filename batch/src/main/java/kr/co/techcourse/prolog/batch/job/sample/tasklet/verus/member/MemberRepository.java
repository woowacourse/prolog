package kr.co.techcourse.prolog.batch.job.sample.tasklet.verus.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("verus.MemberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByName(String name);
}
