package wooteco.prolog.studylog.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.ablity.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

    Optional<Ability> findByIdAndMember(Long id, Member member);
}
