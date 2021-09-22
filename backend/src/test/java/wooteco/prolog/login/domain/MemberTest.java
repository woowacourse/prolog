package wooteco.prolog.login.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.ablity.Ability;
import wooteco.prolog.studylog.exception.AbilityHasChildrenException;
import wooteco.prolog.studylog.exception.AbilityNotFoundException;

class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "saminching", "손너잘", Role.CREW, 1234L, "imageUrl");
    }

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
        // when
        Ability ability = Ability.parent(1L, "역량", "너잘의 3인칭 역량", "파란색", member);

        // then
        assertThat(member.getAbilities()).containsExactly(ability);
    }
}
