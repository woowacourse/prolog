package wooteco.prolog.tag.dto;

import wooteco.prolog.tag.domain.Tag;

public class TagResponse {
    private Long id;
    private String name;

    public TagResponse() {
    }

    public TagResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagResponse of(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
