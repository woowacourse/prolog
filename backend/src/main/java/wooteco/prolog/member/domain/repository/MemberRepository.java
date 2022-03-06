package wooteco.prolog.member.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByGithubId(Long githubId);

    Optional<Member> findByUsername(String username);

    List<Member> findByIdIn(List<Long> memberIds);
}
