package wooteco.prolog.levellogs.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;

public interface SelfDiscussionRepository extends JpaRepository<SelfDiscussion, Long> {

    List<SelfDiscussion> findByLevelLog(LevelLog levelLog);

    List<SelfDiscussion> findAllByLevelLogIn(List<LevelLog> levelLogs);
}
