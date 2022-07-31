package wooteco.prolog.fixtures;

import wooteco.prolog.studylog.application.dto.CommentChangeRequest;
import wooteco.prolog.studylog.application.dto.CommentCreateRequest;

public enum CommentAcceptanceFixture {

    COMMENT("스터디로그의 댓글 내용입니다."),
    UPDATED_COMMENT("수정된 스터디로그의 댓글 내용입니다.");

    private final String content;

    CommentAcceptanceFixture(String content) {
        this.content = content;
    }

    public CommentCreateRequest getCreateRequest() {
        return new CommentCreateRequest(COMMENT.content);
    }

    public CommentChangeRequest getUpdateRequest() {
        return new CommentChangeRequest(UPDATED_COMMENT.content);
    }
}
