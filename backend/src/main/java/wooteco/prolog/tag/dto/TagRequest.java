package wooteco.prolog.tag.dto;

import wooteco.prolog.tag.domain.Tag;

public class TagRequest {

    private String name;

    public TagRequest() {
    }

    public TagRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Tag toEntity(TagRequest tagRequest) {
        return new Tag(tagRequest.getName());
    }
}
