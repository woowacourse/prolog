package wooteco.prolog.session.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.session.domain.SessionMember;

public interface SessionMemberRepository extends JpaRepository<SessionMember, Long> {

    List<SessionMember> findAllBySessionId(Long sessionId);
}
