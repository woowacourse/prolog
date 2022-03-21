package wooteco.prolog.ability.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.ability.domain.DefaultAbility;

public interface DefaultAbilityRepository extends JpaRepository<DefaultAbility, Long> {

    List<DefaultAbility> findByTemplateOrTemplate(String template1, String template2);
}
