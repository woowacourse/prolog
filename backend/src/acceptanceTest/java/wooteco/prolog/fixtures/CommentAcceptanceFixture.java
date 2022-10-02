package wooteco.prolog.fixtures;

import wooteco.prolog.comment.ui.dto.CommentChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentCreateRequest;

public enum CommentAcceptanceFixture {

    STUDY_LOG_COMMENT("스터디로그의 댓글 내용입니다."),
    STUDY_LOG_UPDATE_COMMENT("수정된 스터디로그의 댓글 내용입니다."),
    LEVEL_LOG_COMMENT("레벨로그의 댓글 내용입니다."),
    LEVEL_LOG_UPDATE_COMENT("수정된 레벨로그의 댓글 내용입니다.");

    private final String content;

    CommentAcceptanceFixture(String content) {
        this.content = content;
    }

    public CommentCreateRequest getCreateStudylogRequest() {
        return new CommentCreateRequest(STUDY_LOG_COMMENT.content);
    }

    public CommentChangeRequest getUpdateStudylogRequest() {
        return new CommentChangeRequest(STUDY_LOG_UPDATE_COMMENT.content);
    }

    public CommentCreateRequest getCreateLevellogRequest() {
        return new CommentCreateRequest(STUDY_LOG_COMMENT.content);
    }

    public CommentChangeRequest getUpdateLevellogRequest() {
        return new CommentChangeRequest(STUDY_LOG_UPDATE_COMMENT.content);
    }
}
