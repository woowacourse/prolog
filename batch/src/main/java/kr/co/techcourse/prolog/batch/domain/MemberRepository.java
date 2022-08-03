package kr.co.techcourse.prolog.batch.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByNickname(String nickname);
}
