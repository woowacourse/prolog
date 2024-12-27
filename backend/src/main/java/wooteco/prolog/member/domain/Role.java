package wooteco.prolog.member.domain;

public enum Role {

    GUEST(0),   // 미인증 사용자
    CREW(1),    // 인증 사용자
    COACH(2),   // 운영진
    ADMIN(3);   // 관리자

    private final int importance;

    Role(final int importance) {
        this.importance = importance;
    }

    public boolean hasLowerImportanceThan(final Role role) {
        return this.importance < role.importance;
    }
}
