package kr.co.techcourse.prolog.batch.job.sample.tasklet.eve.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("eve.MemberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String nickname);
}
