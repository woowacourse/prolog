package wooteco.prolog.post.domain;

import wooteco.prolog.post.exception.NotValidSortNameException;

import java.util.Arrays;

public enum SortBy {
    DESC,
    ASC;

    public static SortBy findByName(String name){
        return Arrays.stream(SortBy.values())
                .filter(sortBy -> sortBy.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(NotValidSortNameException::new);
    }
}
