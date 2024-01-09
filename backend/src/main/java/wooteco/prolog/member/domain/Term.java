package wooteco.prolog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Term {

    FIRST("1기"),
    SECOND("2기"),
    THIRD("3기"),
    FOURTH("4기"),
    FIFTH("5기"),
    SIXTH("6기");

    private final String name;

    public static Term getTermByName(String name) {
        return Arrays.stream(values())
            .filter(term -> term.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("name과 일치하는 term이 존재하지 않습니다."));
    }
}
