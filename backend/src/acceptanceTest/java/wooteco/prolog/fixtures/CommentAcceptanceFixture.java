package wooteco.prolog.fixtures;

import wooteco.prolog.studylog.application.dto.CommentCreateRequest;

public enum CommentAcceptanceFixture {

    COMMENT1("스터디로그 1번에 대칭되는 댓글 내용입니다.")
    ;

    private final String content;
    private final CommentCreateRequest request;

    CommentAcceptanceFixture(String content) {
        this.content = content;
        this.request = new CommentCreateRequest(content);
    }

    public CommentCreateRequest getRequest() {
        return request;
    }
}