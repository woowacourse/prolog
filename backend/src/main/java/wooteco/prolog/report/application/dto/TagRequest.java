package wooteco.prolog.report.application.dto;

import wooteco.prolog.studylog.domain.Tag;

public class TagRequest {

    private String name;

    public TagRequest() {
    }

    public TagRequest(String name) {
        this.name = name;
    }

    public static Tag toEntity(TagRequest tagRequest) {
        return new Tag(tagRequest.getName());
    }

    public String getName() {
        return name;
    }
}
