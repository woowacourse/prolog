package wooteco.prolog.login.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberTag;
import wooteco.prolog.member.domain.MemberTags;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.studylog.domain.Tag;

class MemberTest {

    private final String empty = "";

    @DisplayName("constructor : nickname이 없을 때 loginName으로 대체되는지 확인")
    @Test
    void constructor() {
        //given
        final String loginName = "soulg";
        final String existName = "nickname";

        final Member member1 = new Member(1L, "soulg", null, Role.CREW, 1234L, "imageUrl");
        final Member member = new Member(2L, "soulg", empty, Role.CREW, 1234L, "imageUrl");
        final Member member3 = new Member(3L, "soulg", existName, Role.CREW, 1234L, "imageUrl");

        //when, then
        assertThat(member1.getNickname()).isEqualTo(loginName);
        assertThat(member.getNickname()).isEqualTo(loginName);
        assertThat(member3.getNickname()).isEqualTo(existName);
    }

    @DisplayName("updateNickname() : 변경할 닉네임이 빈 값이면 닉네임을 바꾸지 않는다.")
    @Test
    void updateNickname() {
        //given
        final Member member = new Member(1L, "soulg", "judy",
            Role.CREW, 1234L, "imageUrl");

        //when
        member.updateNickname(empty);

        //then
        assertEquals("judy", member.getNickname());
    }

    @DisplayName("updateImageUrl() : 변경할 이미지가 없다면 이미지를 바꾸지 않는다.")
    @Test
    void updateImageUrl() {
        //given
        final Member member = new Member(1L, "soulg", "judy",
            Role.CREW, 1234L, "imageUrl");

        //when
        member.updateImageUrl(empty);

        //then
        assertEquals("imageUrl", member.getImageUrl());
    }

    @DisplayName("getMemberTagsWithSort() : 멤버가 가지고 있는 태그의 수로 내림차순 정렬을 할 수 있다.")
    @Test
    void getMemberTagsWithSort() {
        //given
        final Member member = new Member(1L, "a", "member",
            Role.CREW, 1234L, "imageUrl1");

        List<Tag> tags = LongStream.range(1, 4).boxed()
            .map(id -> new Tag(id, id.toString()))
            .collect(Collectors.toList());

        final MemberTag memberTag1 = new MemberTag(1L, member, tags.get(0), 1);
        final MemberTag memberTag2 = new MemberTag(2L, member, tags.get(1), 2);
        final MemberTag memberTag3 = new MemberTag(3L, member, tags.get(2), 3);
        final MemberTags memberTags = new MemberTags(
            Arrays.asList(memberTag1, memberTag2, memberTag3));

        final Member modifiedMember = new Member(1L, "a", "member",
            Role.CREW, 1234L, "imageUrl1", memberTags);

        final List<MemberTag> expected = Arrays.asList(memberTag3, memberTag2, memberTag1);

        //when
        final List<MemberTag> actual = modifiedMember.getMemberTagsWithSort();

        //then
        assertEquals(actual, expected);
    }
}
