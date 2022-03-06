package wooteco.prolog.studylog.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.StudylogMission;

public interface StudylogMissionRepository extends JpaRepository<StudylogMission, Long> {

    Optional<StudylogMission> findByStudylogIdAndMissionId(Long studylogId, Long missionId);

    Optional<StudylogMission> findByStudylogId(Long studylogId);
}
