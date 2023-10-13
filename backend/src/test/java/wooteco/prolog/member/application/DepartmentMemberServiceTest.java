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
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Department;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.member.domain.repository.DepartmentMemberRepository;

@ExtendWith(MockitoExtension.class)
class DepartmentMemberServiceTest {

    @Mock
    private DepartmentMemberRepository departmentMemberRepository;

    @InjectMocks
    private DepartmentMemberService departmentMemberService;

    @DisplayName("GroupId로 GroupMember를 찾는다.")
    @Test
    void findDepartmentMemberByDepartmentId() {
        //given
        final Long memberId = 1L;
        final Long DepartmentId = 2L;
        final Long groupMemberId = 3L;

        final Member member = new Member(memberId, "송세연", "아마란스", Role.CREW, 1523L, "image");
        final Department department = new Department(DepartmentId, "백엔드", "2023 백엔드");
        final DepartmentMember departmentMember = new DepartmentMember(groupMemberId, member, department);

        when(departmentMemberRepository.findByDepartmentId(any())).thenReturn(ImmutableList.of(departmentMember));

        //when
        final List<DepartmentMember> departmentMembers = departmentMemberService.findDepartmentMemberByDepartmentId(
            DepartmentId);

        //then
        assertThat(departmentMembers).containsExactly(departmentMember);
    }
}
