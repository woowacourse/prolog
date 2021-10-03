package wooteco.prolog.member.util;

import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

public enum MemberFixture {

    나봄("bomin", "나봄", Role.CREW, 100L, "abcd"),
    웨지("wedge", "웨지", Role.CREW, 101L, "sihyung92");

    private final String name;
    private final String nickname;
    private final Role role;
    private final long githubId;
    private final String imageUrl;

    MemberFixture(String name, String nickname, Role role, long githubId, String imageUrl) {
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.githubId = githubId;
        this.imageUrl = imageUrl;
    }

    public Member asDomain() {
        return new Member(name, nickname, role, githubId, imageUrl);
    }

    public String getMemberName() {
        return this.name;
    }
}
