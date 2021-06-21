package wooteco.prolog.login.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        Member member1 = new Member(1L, nullName, "soulg", Role.CREW, 1234L, "imageUrl");
        Member member2 = new Member(2L, emptyName, "soulg", Role.CREW, 1234L, "imageUrl");
        Member member3 = new Member(3L, existName, "soulg", Role.CREW, 1234L, "imageUrl");

        //then
        Assertions.assertThat(member1.getNickname()).isEqualTo(loginName);
        Assertions.assertThat(member2.getNickname()).isEqualTo(loginName);
        Assertions.assertThat(member3.getNickname()).isEqualTo(existName);
    }
}