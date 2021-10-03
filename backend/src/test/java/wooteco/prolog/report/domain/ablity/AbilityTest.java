package wooteco.prolog.report.domain.ablity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.report.exception.AbilityHasChildrenException;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;

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

    @DisplayName("역량 정보를 수정한다.")
    @Test
    void updateAbility() {
        // given
        Long abilityId = 1L;
        Ability ability = Ability.parent(abilityId, "역량", "너잘의 3인칭 역량", "파란색", member);
        Ability updateTarget = new Ability(abilityId, "새로운 역량", "그것은 피카를 사랑하는 힘", "핑크색");

        // when
        ability.updateWithValidation(updateTarget, new ArrayList<>());

        // then
        assertThat(ability).usingRecursiveComparison()
            .ignoringFields("parent", "children", "member")
            .isEqualTo(updateTarget);
    }
    
    @DisplayName("삭제가 가능한 상태인지 검증할 때")
    @Nested
    class Removable {
        
        @DisplayName("자식 역량이 없는 경우 예외가 발생하지 않는다.")
        @Test
        void isRemovable() {
            // given
            Ability ability = Ability.parent(1L, "역량", "너잘의 3인칭 역량", "파란색", member);

            // when, then
            assertThatCode(ability::validateDeletable).doesNotThrowAnyException();
        }

        @DisplayName("자식 역량이 있는 경우 예외가 발생한다.")
        @Test
        void isNotRemovable() {
            // given
            Ability parentAbility = Ability.parent(1L, "역량", "너잘의 3인칭 역량", "파란색", member);
            Ability childAbility = Ability.child(2L, "역량", "너잘의 3인칭 역량", "파란색", parentAbility, member);

            // when, then
            assertThatThrownBy(parentAbility::validateDeletable).isExactlyInstanceOf(AbilityHasChildrenException.class);
        }
    }

    @DisplayName("역량끼리 이름이 같은 경우 예외가 발생한다.")
    @Test
    void nameDuplicateException() {
        // given
        String name = "역량";

        Ability ability1 = Ability.parent(1L, "역량", "너잘의 3인칭 역량", "파란색", member);
        Ability ability2 = Ability.parent(2L, "역량", "아따따뚜겐", "초록색", member);

        // when, then
        assertThatThrownBy(() -> ability1.validateDuplicateName(Collections.singletonList(ability2)))
            .isExactlyInstanceOf(AbilityNameDuplicateException.class);
    }

    @DisplayName("서로 다른 역량끼리 색상이 같은 경우 예외가 발생한다.")
    @Test
    void colorDuplicateException() {
        // given
        String color = "색상";

        Ability ability1 = Ability.parent(1L, "역량1", "너잘의 3인칭 역량", color, member);
        Ability ability2 = Ability.parent(2L, "역량2", "아따따뚜겐", color, member);

        // when, then
        assertThatThrownBy(() -> ability1.validateDuplicateColor(Collections.singletonList(ability2)))
            .isExactlyInstanceOf(AbilityParentColorDuplicateException.class);
    }

    @DisplayName("부모 자식 역량끼리 색상이 다른 경우 예외가 발생한다.")
    @Test
    void colorDifferentBetweenParentAndChildException() {
        // given
        Ability parentAbility = Ability.parent(1L, "역량1", "너잘의 3인칭 역량", "색상1", member);
        Ability anotherParentAbility = Ability.parent(2L, "역량2", "아따따뚜겐", "색상2", member);
        Ability childAbility = Ability.child(3L, "역량1의 자식역량", "너잘의 3인칭 역량", "색상3", parentAbility, member);

        // when, then
        assertThatThrownBy(() -> childAbility.validateColorWithParent(Collections.singletonList(anotherParentAbility), parentAbility))
            .isExactlyInstanceOf(AbilityParentChildColorDifferentException.class);
    }
}
