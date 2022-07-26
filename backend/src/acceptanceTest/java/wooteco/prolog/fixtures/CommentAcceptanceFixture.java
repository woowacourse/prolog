package wooteco.prolog.fixtures;

import wooteco.prolog.studylog.application.dto.CommentChangeRequest;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;

public enum CommentAcceptanceFixture {

    COMMENT("스터디로그의 댓글 내용입니다."),
    UPDATED_COMMENT("수정된 스터디로그의 댓글 내용입니다.");

    private final String content;
    private final CommentCreateRequest createRequest;
    private final CommentChangeRequest changeRequest;

    CommentAcceptanceFixture(String content) {
        this.content = content;
        this.createRequest = new CommentCreateRequest(content);
        this.changeRequest = new CommentChangeRequest(content);
    }

    public CommentCreateRequest getCreateRequest() {
        return createRequest;
    }
}
