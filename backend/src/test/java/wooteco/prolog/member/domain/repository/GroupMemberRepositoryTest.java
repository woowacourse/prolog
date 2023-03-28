package wooteco.prolog.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.Role;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class GroupMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @Test
    @DisplayName("작성된 studylog의 Member가 GroupMember의 MemberGroup에 포함되는 경우 true를 반환한다.")
    void existsGroupMemberByMemberAndMemberGroup() {
        // given
        Member saveMember = memberRepository.save(
            new Member("username", "nickname", Role.CREW, 1L, "imageUrl"));
        MemberGroup saveMemberGroup = memberGroupRepository.save(
            new MemberGroup(null, "프론트엔드", "프론트엔드 설명")
        );
        groupMemberRepository.save(
            new GroupMember(null, saveMember, saveMemberGroup)
        );

        // when
        boolean extract = groupMemberRepository.existsGroupMemberByMemberAndGroup(
            saveMember, saveMemberGroup);

        // then
        assertThat(extract).isTrue();
    }
}
