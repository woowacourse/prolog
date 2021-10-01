package wooteco.prolog.report.domain.ablity.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.domain.ablity.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    Optional<Ability> findByIdAndMember(Long id, Member member);

    List<Ability> findByMemberAndParentIsNull(Member member);

    List<Ability> findByMember(Member member);

    @Query("select count(a) from Ability a where a.id in :abilityIds and a.parent is null")
    Long countParentAbilitiesOf(List<Long> abilityIds);
}
