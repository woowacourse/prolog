package wooteco.prolog.member.application;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.repository.DepartmentMemberRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DepartmentMemberService {

    private DepartmentMemberRepository departmentMemberRepository;

    public List<DepartmentMember> findDepartmentMemberByDepartmentId(Long departmentId) {
        return departmentMemberRepository.findByDepartmentId(departmentId);
    }

}
