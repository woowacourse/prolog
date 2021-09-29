package wooteco.prolog.fixtures;

import java.util.Arrays;
import java.util.Objects;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.studylog.domain.ablity.Ability;

public enum AbilityFixture {
    PROGRAMMING("프로그래밍", "프로그래밍 관련입니다.", "red"),
    LANGUAGE("언어", null, "red"),
    FRAMEWORK( "프레임워크", null, "red"),
    DESIGN("디자인", "디자인 관련입니다.", "blue"),
    TDD("TDD", null, "blue"),
    ATDD("ATDD", null, "blue"),
    INFRASTRUCTURE("인프라", "인프라 관렵입니다.", "green"),
    WEB_SERVER("웹 서버", null, "green"),
    PROXY("프록시", null, "green");

    private final String name;
    private final String description;
    private final String color;

    AbilityFixture(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public static AbilityFixture findByName(String abilityName) {
        return Arrays.stream(values())
            .filter(value -> value.name.equals(abilityName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 역량입니다."));
    }

    public Ability toAbility(Long id, Ability parent, Member member) {
        if(!Objects.isNull(parent)) {
            return Ability.child(id, name, description, color, parent, member);
        }
        return Ability.parent(id, name, description, color, member);
    }

    public String toJson(Long parent) {
        String format = String.join(System.lineSeparator(), Arrays.asList(
            "{",
            "  name: %s",
            "  description: %s",
            "  color: %s",
            "  parent: %d",
            "}"
            )
        );

        return String.format(format,
            name,
            description,
            color,
            parent
        );
    }
}
