package wooteco.prolog.member.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberGroups {

    private List<MemberGroup> values;

    public boolean isContainsMemberGroups(GroupMember groupMember) {
        return values.contains(groupMember.getGroup());
    }
}
