package wooteco.prolog.login.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.MemberTags;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.Tag;

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

    @Test
    @DisplayName("updateNickname() : 변경할 닉네임이 빈 값이면 닉네임을 바꾸지 않는다.")
    void updateNickname() {
        //given
        Member member = new Member(1L, "soulg", "judy",
            Role.CREW, 1234L, "imageUrl");

        //when
        member.updateNickname("");

        //then
        assertEquals("judy", member.getNickname());
    }

    @Test
    @DisplayName("updateImageUrl() : 변경할 이미지가 없다면 이미지를 바꾸지 않는다.")
    void updateImageUrl() {
        //given
        Member member = new Member(1L, "soulg", "judy",
            Role.CREW, 1234L, "imageUrl");

        //when
        member.updateImageUrl("");

        //then
        assertEquals("imageUrl", member.getImageUrl());
    }

    @Test
    @DisplayName("updateProfileIntro() : 변경할 ProfileIntro가 없다면 ProfileIntro를 바꾸지 않는다.")
    void updateProfileIntro() {
        //given
        final Member member = new Member(1L, "soulg", "judy",
            Role.CREW, 1234L, "imageUrl");
        final String profileIntro = "intro";

        member.updateProfileIntro(profileIntro);

        //when
        member.updateProfileIntro("");

        //then
        assertEquals(profileIntro, member.getProfileIntro());
    }

    @Test
    @DisplayName("getMemberTagsWithSort() : 멤버가 가지고 있는 태그의 수로 내림차순 정렬을 할 수 있다.")
    void test_ifAbsentReplace() {
        //given
        final Member member = new Member(1L, "a", "member",
            Role.CREW, 1234L, "imageUrl1");

        final Tag tag1 = new Tag(1L, "tag1");
        final Tag tag2 = new Tag(2L, "tag2");

        final MemberTag memberTag1 = new MemberTag(1L, member, tag1, 1);
        final MemberTag memberTag2 = new MemberTag(2L, member, tag2, 2);

        final MemberTags memberTags = new MemberTags(Arrays.asList(memberTag1, memberTag2));

        //when
        final Member modifiedMember = new Member(1L, "a", "member",
            Role.CREW, 1234L, "imageUrl1", memberTags);

        final List<MemberTag> memberTagsWithSort = modifiedMember.getMemberTagsWithSort();

        final List<MemberTag> expected = Arrays.asList(memberTag2, memberTag1);

        //then
        assertEquals(memberTagsWithSort, expected);
    }
}
