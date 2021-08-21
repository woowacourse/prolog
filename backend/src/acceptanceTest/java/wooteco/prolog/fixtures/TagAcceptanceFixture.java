package wooteco.prolog.fixtures;

import wooteco.prolog.post.application.dto.PostRequest;
import wooteco.prolog.tag.dto.TagRequest;

import java.util.Arrays;

public enum TagAcceptanceFixture {
    TAG1("자바"),
    TAG2("Optional"),
    TAG3("자바스크립트"),
    TAG4("비동기"),
    TAG5("자료구조"),
    TAG6("알고리즘");

    TagAcceptanceFixture(String name) {
        this.tagRequest = new TagRequest(name);
    }

    private TagRequest tagRequest;

    public TagRequest getTagRequest() {
        return tagRequest;
    }
}
