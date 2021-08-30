package wooteco.prolog.studylog.domain.ablity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AbilityTest {

    @DisplayName("자식 역량을 부모 역량에 추가한다.")
    @Test
    void addChildAbility() {
        Ability parentAbility = Ability.parent(1L, "Language", "discription", "red");
        Ability childAbility = Ability.child(2L, "Language", "discription", "red", parentAbility);
        parentAbility.addChildAbility(childAbility);

        assertThat(parentAbility.getChildren())
            .usingRecursiveComparison()
            .isEqualTo(Collections.singletonList(childAbility));

        assertThat(childAbility.getParent()).isEqualTo(parentAbility);
    }

    @DisplayName("부모 역량인지 확인한다.")
    @ParameterizedTest
    @MethodSource("parametersForIsParent")
    void isParent(Ability ability, boolean expected) {
        assertThat(ability.isParent()).isEqualTo(expected);
    }

    private static Stream<Arguments> parametersForIsParent() {
        Ability parent = Ability.parent(1L, "Language", "discription", "red");

        return Stream.of(
            Arguments.of(parent, true),
            Arguments.of(Ability.child(2L, "Language", "discription", "red", parent), false)
        );
    }
}
