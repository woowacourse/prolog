package wooteco.prolog.post.domain;

import java.util.Arrays;
import wooteco.prolog.post.exception.NotValidSortNameException;

public enum Direction {
    DESC,
    ASC;

    public static Direction findByName(String name){
        return Arrays.stream(Direction.values())
                .filter(sortBy -> sortBy.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(NotValidSortNameException::new);
    }
}
