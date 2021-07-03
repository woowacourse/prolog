package wooteco.prolog.member.domain;

import wooteco.prolog.login.excetpion.RoleNameNotFoundException;

import java.util.Arrays;

public enum Role {

    UNVALIDATED,
    CREW,
    COACH,
    ADMIN;

    public static Role of(String role) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(role))
                .findFirst()
                .orElseThrow(() -> new RoleNameNotFoundException());
    }
}
