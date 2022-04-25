package wooteco.prolog.ability.domain.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.ability.domain.StudylogAbility;

public interface StudylogAbilityRepository extends JpaRepository<StudylogAbility, Long> {

    List<StudylogAbility> findByMemberId(Long memberId);

    Page<StudylogAbility> findByMemberId(Long memberId, Pageable pageable);

    Page<StudylogAbility> findByAbilityIdIn(List<Long> abilityIds, Pageable pageable);

    List<StudylogAbility> findByStudylogIdIn(List<Long> studylogIds);

    void deleteByStudylogId(Long studylogId);
}
