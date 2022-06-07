package wooteco.prolog.common.fixture.member;

import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

public class MemberFixture {

    public static Member crewMember1() {
        return new Member("crew member1", "crew member1", Role.CREW, 1L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member crewMember2() {
        return new Member("crew member2", "crew member2", Role.CREW, 2L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member crewMember3() {
        return new Member("crew member3", "crew member3", Role.CREW, 3L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member coachMemberMember1() {
        return new Member("coach member1", "coach member1", Role.COACH, 3L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member coachMemberMember2() {
        return new Member("coach member2", "coach member2", Role.COACH, 3L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member coachMemberMember3() {
        return new Member("coach member3", "coach member3", Role.COACH, 3L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member AdminMemberMember1() {
        return new Member("admin member1", "admin member1", Role.ADMIN, 1L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member AdminMemberMember2() {
        return new Member("coach member2", "coach member2", Role.ADMIN, 2L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }

    public static Member AdminMemberMember3() {
        return new Member("coach member3", "coach member3", Role.ADMIN, 3L,
                          "https://avatars.githubusercontent.com/u/51393021?v=4");
    }
}
