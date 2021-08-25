package wooteco.prolog.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByGithubId(Long githubId);

    Optional<Member> findByUsername(String username);
}
