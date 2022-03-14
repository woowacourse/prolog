package wooteco.prolog.member.application;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.prolog.member.domain.GroupMember;
import wooteco.prolog.member.domain.repository.GroupMemberRepository;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberService {

    private GroupMemberRepository groupMemberRepository;

    public List<GroupMember> findGroupMemberByGroupId(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }
}
