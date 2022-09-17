package wooteco.prolog.fixtures;

import wooteco.prolog.comment.ui.dto.CommentStudylogChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentStudylogCreateRequest;

public enum CommentAcceptanceFixture {

    COMMENT("스터디로그의 댓글 내용입니다."),
    UPDATED_COMMENT("수정된 스터디로그의 댓글 내용입니다.");

    private final String content;

    CommentAcceptanceFixture(String content) {
        this.content = content;
    }

    public CommentStudylogCreateRequest getCreateRequest() {
        return new CommentStudylogCreateRequest(COMMENT.content);
    }

    public CommentStudylogChangeRequest getUpdateRequest() {
        return new CommentStudylogChangeRequest(UPDATED_COMMENT.content);
    }
}
