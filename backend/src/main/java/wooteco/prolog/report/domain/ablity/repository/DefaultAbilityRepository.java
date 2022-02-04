package wooteco.prolog.report.domain.ablity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.report.domain.ablity.DefaultAbility;

public interface DefaultAbilityRepository extends JpaRepository<DefaultAbility, Long> {

    List<DefaultAbility> findByTemplateOrTemplate(String template1, String template2);
}
