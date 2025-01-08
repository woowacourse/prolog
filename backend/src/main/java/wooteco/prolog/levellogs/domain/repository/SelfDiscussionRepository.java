package wooteco.prolog.levellogs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.levellogs.domain.LevelLog;
import wooteco.prolog.levellogs.domain.SelfDiscussion;

import java.util.List;

public interface SelfDiscussionRepository extends JpaRepository<SelfDiscussion, Long> {

    List<SelfDiscussion> findByLevelLog(LevelLog levelLog);

    List<SelfDiscussion> findAllByLevelLogIn(List<LevelLog> levelLogs);

    void deleteByLevelLog(LevelLog levelLog);
}
