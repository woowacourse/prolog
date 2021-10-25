package wooteco.prolog.report.domain.ablity.repository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wooteco.prolog.report.domain.ablity.DefaultAbility;

@DataJpaTest
class DefaultAbilityRepositoryTest {

    @Autowired
    private DefaultAbilityRepository defaultAbilityRepository;

    @DisplayName("Template 타입이 common 또는 be인 역량 목록을 조회한다.")
    @Test
    void findByTemplateOrTemplate() {
        // given
        DefaultAbility defaultAbility = defaultAbilityRepository.save(
            new DefaultAbility("프로그래밍", "프로그래밍 역량", "#111111", "be", null)
        );

        // when
        List<DefaultAbility> defaultAbilities = defaultAbilityRepository.findByTemplateOrTemplate("common", "be");

        // then
        assertThat(defaultAbilities).containsExactly(defaultAbility);
    }
}