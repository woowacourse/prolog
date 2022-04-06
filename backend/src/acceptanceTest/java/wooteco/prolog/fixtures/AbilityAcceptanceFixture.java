package wooteco.prolog.fixtures;

import java.util.Arrays;
import wooteco.prolog.ability.application.dto.AbilityCreateRequest;

public enum AbilityAcceptanceFixture {
    PROGRAMMING("프로그래밍", "프로그래밍 관련입니다.", "red"),
    LANGUAGE("언어", null, "red"),
    FRAMEWORK( "프레임워크", null, "red2"),
    SPRING( "스프링", "스프링입니다", "red2"),
    UIUX( "UI/UX", null, "orange2"),
    UIUXDESIGN( "UI/UX 설계", "UI/UX 설계 입니다", "orange2"),
    CS( "CS", null, "orange"),
    NETWORK( "네트워크", "네트워크 입니다", "orange"),
    CREATIVITY("프로그래밍과 색상이 같은 창의력", "창의력입니다.", "red"),
    THINK_POWER("프로그래밍과 색상이 다른 사고력", "사고력입니다.", "black"),
    DESIGN("디자인", "디자인 관련입니다.", "blue"),
    TDD("TDD", null, "blue"),
    ATDD("ATDD", null, "blue"),
    INFRASTRUCTURE("인프라", "인프라 관렵입니다.", "green"),
    WEB_SERVER("웹 서버", null, "green"),
    PROXY("프록시", null, "green");

    private final String name;
    private final String description;
    private final String color;

    AbilityAcceptanceFixture(String name, String description, String color) {
        this.name = name;
        this.description = description;
        this.color = color;
    }

    public static AbilityAcceptanceFixture findByName(String abilityName) {
        return Arrays.stream(values())
            .filter(value -> value.name.equals(abilityName))
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 역량입니다."));
    }

    public AbilityCreateRequest toCreateRequestWithParentId(Long parentId) {
        return new AbilityCreateRequest(name, description, color, parentId);
    }
}
