package wooteco.prolog.member.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wooteco.prolog.member.domain.Department;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.Member;

public interface DepartmentMemberRepository extends JpaRepository<DepartmentMember, Long> {

    List<DepartmentMember> findByDepartmentId(Long departmentId);

    boolean existsDepartmentMemberByMemberAndDepartment(Member member, Department department);
}
