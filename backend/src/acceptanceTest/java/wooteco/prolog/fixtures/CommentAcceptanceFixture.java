package wooteco.prolog.fixtures;

import wooteco.prolog.comment.ui.dto.CommentLevellogCreateRequest;
import wooteco.prolog.comment.ui.dto.CommentStudylogChangeRequest;
import wooteco.prolog.comment.ui.dto.CommentStudylogCreateRequest;

public enum CommentAcceptanceFixture {

    STUDY_LOG_COMMENT("스터디로그의 댓글 내용입니다."),
    STUDY_LOG_UPDATE_COMMENT("수정된 스터디로그의 댓글 내용입니다."),
    LEVEL_LOG_COMMENT("레벨로그의 댓글 내용입니다."),
    LEVEL_LOG_UPDATE_COMENT("수정된 레벨로그의 댓글 내용입니다.");

    private final String content;

    CommentAcceptanceFixture(String content) {
        this.content = content;
    }

    public CommentStudylogCreateRequest getCreateStudylogRequest() {
        return new CommentStudylogCreateRequest(STUDY_LOG_COMMENT.content);
    }

    public CommentStudylogChangeRequest getUpdateStudylogRequest() {
        return new CommentStudylogChangeRequest(STUDY_LOG_UPDATE_COMMENT.content);
    }

    public CommentLevellogCreateRequest getCreateLevellogRequest() {
        return new CommentLevellogCreateRequest(STUDY_LOG_COMMENT.content);
    }

    public CommentLevellogCreateRequest getUpdateLevellogRequest() {
        return new CommentLevellogCreateRequest(STUDY_LOG_UPDATE_COMMENT.content);
    }
}
