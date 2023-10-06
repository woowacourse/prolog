package wooteco.prolog.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Part {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    ANDROID("안드로이드");

    private final String name;

    public boolean isContainedBy(String name) {
        if (name == null) {
            return false;
        }
        return Arrays.stream(values())
            .anyMatch(p -> p.name.equals(name));
    }

    public static Part getPartByName(String name) {
        return Arrays.stream(values())
            .filter(part -> part.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("name과 일치하는 part가 존재하지 않습니다."));
    }
}
