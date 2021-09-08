package wooteco.prolog.member.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import wooteco.prolog.member.exception.NotExistsMemberTag;

@Getter
@Embeddable
@NoArgsConstructor
public class MemberTags {

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<MemberTag> values = new ArrayList<>();

    public void addMemberTags(List<MemberTag> memberTags) {
        for (MemberTag memberTag : memberTags) {
            add(memberTag);
        }
    }

    public void add(MemberTag memberTag) {
        final Optional<MemberTag> foundMemberTag = findMemberTag(memberTag);
        if (foundMemberTag.isPresent()) {
            foundMemberTag.get().addCount();
            return;
        }
        values.add(memberTag);
    }

    public void removeMemberTags(List<MemberTag> memberTags) {
        for (MemberTag memberTag : memberTags) {
            remove(memberTag);
        }
    }

    private void remove(MemberTag memberTag) {
        final MemberTag foundMemberTag =
                findMemberTag(memberTag).orElseThrow(NotExistsMemberTag::new);

        if (foundMemberTag.hasOnlyOne()) {
            values.remove(foundMemberTag);
            return;
        }
        foundMemberTag.decreaseCount();
    }

    private Optional<MemberTag> findMemberTag(MemberTag memberTag) {
        return values.stream()
                .filter(value -> value.isSame(memberTag))
                .findAny();
    }

    public void updateTags(List<MemberTag> originalMemberTag, List<MemberTag> newMemberTag) {
        removeMemberTags(originalMemberTag);
        addMemberTags(newMemberTag);
    }
}
