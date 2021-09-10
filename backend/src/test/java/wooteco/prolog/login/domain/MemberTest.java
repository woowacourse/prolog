package wooteco.prolog.login.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;

class MemberTest {

    @DisplayName("nickname이 없을 때 loginName으로 대체되는지 확인")
    @Test
    void ifAbsentNickName() {
        //given
        String loginName = "soulg";
        String nullName = null;
        String emptyName = "";
        String existName = "nickname";

        //when
        Member member1 = new Member(1L, "soulg", nullName, Role.CREW, 1234L, "imageUrl");
        Member member = new Member(2L, "soulg", emptyName, Role.CREW, 1234L, "imageUrl");
        Member member3 = new Member(3L, "soulg", existName, Role.CREW, 1234L, "imageUrl");

        //then
        assertThat(member1.getNickname()).isEqualTo(loginName);
        assertThat(member.getNickname()).isEqualTo(loginName);
        assertThat(member3.getNickname()).isEqualTo(existName);
    }

    @DisplayName("역량 생성시 자동으로 역량 목록에 추가된다.")
    @Test
    void addAbility() {
        // given
        Member member = Member를_생성한다();

        // when
        Ability ability = Ability.parent(1L, "역량", "너잘의 3인칭 역량", "파란색", member);

        // then
        assertThat(member.getAbilities()).containsExactly(ability);
    }

    @DisplayName("역량을 수정한다.")
    @Test
    void updateAbility() {
        // given
        Member member = Member를_생성한다();

        Long abilityId = 1L;
        Ability legacyAbility = Ability.parent(abilityId, "역량", "너잘의 3인칭 역량", "파란색", member);
        Ability updateTarget = Ability.updateTarget(abilityId, "새로운 역량", "그것은 피카를 사랑하는 힘", "핑크색");

        // when
        member.updateAbility(updateTarget);
        Ability updatedAbility = getUpdatedAbility(member, abilityId);

        // then
        assertThat(updatedAbility).usingRecursiveComparison()
            .ignoringFields("parent", "children", "member")
            .isEqualTo(legacyAbility);
    }

    private Member Member를_생성한다() {
        return new Member(1L, "saminching", "손너잘", Role.CREW, 1234L, "imageUrl");
    }

    private Ability getUpdatedAbility(Member member, Long abilityId) {
        return member.getAbilities()
            .stream()
            .filter(ability -> ability.getId().equals(abilityId))
            .findAny()
            .orElseThrow(AbilityNotFoundException::new);
    }

    @DisplayName("역량 수정시 일치하는 역량이 없을 경우 예외가 발생한다.")
    @Test
    void updateAbilityException() {
        // given
        Member member = Member를_생성한다();

        Ability updateTarget = Ability.updateTarget(1L, "새로운 역량", "그것은 피카를 사랑하는 힘", "핑크색");

        // when, then
        assertThatThrownBy(() -> member.updateAbility(updateTarget)).isExactlyInstanceOf(AbilityNotFoundException.class);
    }
}