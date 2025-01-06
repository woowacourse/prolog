package wooteco.prolog.session.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.session.domain.SessionMember;

import java.util.List;
import java.util.Optional;

public interface SessionMemberRepository extends JpaRepository<SessionMember, Long> {

    List<SessionMember> findAllBySessionId(Long sessionId);

    List<SessionMember> findByMember(Member member);

    Optional<SessionMember> findBySessionIdAndMember(Long sessionId, Member member);
}
