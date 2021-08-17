package wooteco.prolog.member.domain;

import java.util.Arrays;
import wooteco.prolog.login.excetpion.RoleNameNotFoundException;

public enum Role {

    UNVALIDATED,
    CREW,
    COACH,
    ADMIN;

    public static Role of(String role) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(RoleNameNotFoundException::new);
    }
}
