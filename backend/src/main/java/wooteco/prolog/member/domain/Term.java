package wooteco.prolog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
