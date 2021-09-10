package wooteco.prolog.studylog.domain.ablity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

class AbilityTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = mock(Member.class);
    }

    @DisplayName("자식 역량 생성시 부모 역량과 자동으로 관계를 맺는다.")
    @Test
    void addChildAbility() {
        Ability parentAbility = Ability.parent(1L, "Language", "discription", "red", member);
        Ability childAbility = Ability.child(2L, "Language", "discription", "red", parentAbility, member);

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
        Member member = mock(Member.class);
        Ability parent = Ability.parent(1L, "Language", "discription", "red", member);
        
        return Stream.of(
            Arguments.of(parent, true),
            Arguments.of(Ability.child(2L, "Language", "discription", "red", parent, member), false)
        );
    }
    
    @DisplayName("역량 생성시 자동으로 Member와 연결된다.")
    @Test
    void memberAddAbility() {
        // when
        Ability parent = Ability.parent(1L, "Language", "discription", "red", member);
        
        // then
        verify(member).addAbility(parent);
    }

    @DisplayName("역량 정보를 수정한다.")
    @Test
    void updateAbility() {
        // given
        Long abilityId = 1L;
        Ability ability = Ability.parent(abilityId, "역량", "너잘의 3인칭 역량", "파란색", member);
        Ability updateTarget = Ability.updateTarget(abilityId, "새로운 역량", "그것은 피카를 사랑하는 힘", "핑크색");

        // when
        ability.update(updateTarget);

        // then
        assertThat(ability).usingRecursiveComparison()
            .ignoringFields("parent", "children", "member")
            .isEqualTo(updateTarget);
    }
}
