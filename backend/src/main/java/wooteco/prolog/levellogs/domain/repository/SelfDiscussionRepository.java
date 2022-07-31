package wooteco.prolog.levellogs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.levellogs.domain.SelfDiscussion;

public interface SelfDiscussionRepository extends JpaRepository<SelfDiscussion, Long> {
}
