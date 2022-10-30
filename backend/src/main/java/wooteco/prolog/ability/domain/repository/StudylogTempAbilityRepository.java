package wooteco.prolog.ability.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.ability.domain.StudylogTempAbility;

public interface StudylogTempAbilityRepository extends JpaRepository<StudylogTempAbility, Long> {

    void deleteByStudylogTempId(Long studylogTempId);

    List<StudylogTempAbility> findByStudylogTempId(Long studylogTempId);
}
