package wooteco.prolog.fixtures;

import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;

public enum TagAcceptanceFixture {
    TAG1(1L, "자바"),
    TAG2(2L, "Optional"),
    TAG3(3L, "자바스크립트"),
    TAG4(4L, "비동기"),
    TAG5(5L, "자료구조"),
    TAG6(6L, "알고리즘");

    TagAcceptanceFixture(Long id, String name) {
        this.tagId = id;
        this.tagRequest = new TagRequest(name);
    }

    private Long tagId;
    private TagRequest tagRequest;

    public TagRequest getTagRequest() {
        return tagRequest;
    }

    public Long getTagId() {
        return tagId;
    }
}
