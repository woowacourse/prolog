package wooteco.prolog.member.domain.repository;

import wooteco.prolog.member.domain.MemberTags;

public interface MemberTagRepository {

    void register(MemberTags memberTags);

    void update(MemberTags originalMemberTags, MemberTags newMemberTags);

    void unregister(MemberTags memberTags);
}
