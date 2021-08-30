package wooteco.prolog.studylog.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.studylog.domain.ablity.Ability;

public interface AbilityRepository extends JpaRepository<Ability, Long> {
}
