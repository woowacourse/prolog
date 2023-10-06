package wooteco.prolog.member.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.DepartmentMember;
import wooteco.prolog.member.domain.repository.DepartmentMemberRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberService {

    private DepartmentMemberRepository departmentMemberRepository;

    public List<DepartmentMember> findGroupMemberByGroupId(Long groupId) {
        return departmentMemberRepository.findByDepartmentId(groupId);
    }

}
