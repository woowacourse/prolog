package wooteco.prolog.member.domain;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Part {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    ANDROID("안드로이드");

    private final String name;

    public static Part getPartByName(String name) {
        return Arrays.stream(values())
            .filter(part -> part.name.equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("name과 일치하는 part가 존재하지 않습니다."));
    }
}
