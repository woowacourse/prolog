package wooteco.prolog.member.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Department;
import wooteco.prolog.member.domain.Role;
import wooteco.support.utils.RepositoryTest;

@RepositoryTest
class DepartmentMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DepartmentMemberRepository departmentMemberRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("작성된 studylog의 Member가 DepartmentMember의 Department에 포함되는 경우 true를 반환한다.")
    void existsDepartmentMemberByMemberAndDepartment() {
        // given
        Member saveMember = memberRepository.save(
            new Member("username", "nickname", Role.CREW, 1L, "imageUrl"));
        Department saveDepartment = departmentRepository.save(
            new Department(null, "프론트엔드", "5")
        );
        departmentMemberRepository.save(
            new DepartmentMember(null, saveMember, saveDepartment)
        );

        // when
        boolean extract = departmentMemberRepository.existsDepartmentMemberByMemberAndDepartment(
            saveMember, saveDepartment);

        // then
        assertThat(extract).isTrue();
    }
}
