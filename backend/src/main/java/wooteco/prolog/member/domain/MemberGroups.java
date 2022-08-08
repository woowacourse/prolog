package wooteco.prolog.member.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberGroups {

    private List<MemberGroup> memberGroups;

    public boolean isContainsMemberGroups(GroupMember groupMember) {
        return memberGroups.contains(groupMember.getGroup());
    }
}
