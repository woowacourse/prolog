package wooteco.prolog.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.MemberGroup;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;

@ExtendWith(MockitoExtension.class)
class GroupMemberServiceTest {

    @Mock
    private GroupMemberRepository groupMemberRepository;

    @InjectMocks
    private GroupMemberService groupMemberService;

    @DisplayName("GroupId로 GroupMember를 찾는다.")
    @Test
    void findGroupMemberByGroupId() {
        //given
        final Long memberId = 1L;
        final Long memberGroupId = 2L;
        final Long groupMemberId = 3L;

        final Member member = new Member(memberId, "송세연", "아마란스", Role.CREW, 1523L, "image");
        final MemberGroup memberGroup = new MemberGroup(memberGroupId, "백엔드", "2023 백엔드");
        final GroupMember groupMember = new GroupMember(groupMemberId, member, memberGroup);

        when(groupMemberRepository.findByGroupId(any())).thenReturn(ImmutableList.of(groupMember));

        //when
        final List<GroupMember> groupMembers = groupMemberService.findGroupMemberByGroupId(memberGroupId);

        //then
        assertThat(groupMembers).containsExactly(groupMember);
    }
}
