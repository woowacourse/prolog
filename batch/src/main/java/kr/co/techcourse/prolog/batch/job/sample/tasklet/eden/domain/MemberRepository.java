package kr.co.techcourse.prolog.batch.job.sample.tasklet.eden.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository("eden.MemberRepository")
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByName(String name);
}
