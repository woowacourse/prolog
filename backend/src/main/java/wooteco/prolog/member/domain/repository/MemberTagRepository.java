package wooteco.prolog.member.domain.repository;

import java.util.List;
import wooteco.prolog.member.domain.MemberTag;

public interface MemberTagRepository {

    void register(List<MemberTag> memberTags);

    void update(List<MemberTag> originalMemberTags, List<MemberTag> newMemberTags);

    void unregister(List<MemberTag> memberTags);
}
