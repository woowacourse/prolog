package wooteco.prolog.login;

import java.util.Arrays;

public enum Role {

    UNVALIDATED,
    CREW,
    COACH;

    public static Role of(String role) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("역할을 찾을 수 없습니다."));
    }
}
