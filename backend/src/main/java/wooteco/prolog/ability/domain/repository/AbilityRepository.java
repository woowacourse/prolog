package wooteco.prolog.ability.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.ability.domain.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    Optional<Ability> findByIdAndMemberId(Long abilityId, Long memberId);

    List<Ability> findByMemberIdAndParentIsNull(Long memberId);

    List<Ability> findByMemberId(Long memberId);

    List<Ability> findByColorAndParentIsNull(String color);

    @Query("select count(a) from Ability a where a.id in :abilityIds and a.parent is null")
    Long countParentAbilitiesOf(List<Long> abilityIds);

    @Query("select a from Ability a where a.parent.id in (:parentIds)")
    List<Ability> findChildrenAbilitiesByParentId(List<Long> parentIds);
}
