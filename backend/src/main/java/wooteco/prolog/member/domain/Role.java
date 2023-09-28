package wooteco.prolog.member.domain;

public enum Role {

    GUEST(0),
    CREW(1),
    COACH(2),
    ADMIN(3);

    private final int importance;

    Role(final int importance) {
        this.importance = importance;
    }

    public boolean hasLowerImportanceThan(final Role role) {
        return this.importance < role.importance;
    }
}
