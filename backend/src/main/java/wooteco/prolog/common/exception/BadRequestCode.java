package wooteco.prolog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.levellogs.exception.InvalidLevelLogAuthorException;
import wooteco.prolog.levellogs.exception.LevelLogNotFoundException;
import wooteco.prolog.login.excetpion.*;
import wooteco.prolog.member.exception.DuplicateMemberTagException;
import wooteco.prolog.member.exception.MemberNotAllowedException;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.roadmap.exception.*;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.studylog.domain.TagName;
import wooteco.prolog.studylog.domain.Title;
import wooteco.prolog.studylog.exception.*;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BadRequestCode {
    NOT_FOUND_ERROR_CODE(0000, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),

    GITHUB_API_FAIL(1000, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다.", GithubApiFailException.class),
    GITHUB_CONNECTION_FAIL(1001, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다.",
        GithubConnectionException.class),
    TOKEN_NOT_VALID(1002, "JWT 토큰이 유효하지 않습니다.", TokenNotValidException.class),
    ROLE_NAME_NOT_FOUND(1003, "해당 이름을 가진 역할이 없습니다.", RoleNameNotFoundException.class),
    MEMBER_NOT_FOUND(1004, "해당 ID를 가진 멤버가 없습니다.", MemberNotFoundException.class),
    MEMBER_NOT_ALLOWED(1005, "권한이 없습니다.", MemberNotAllowedException.class),

    STUDYLOG_ARGUMENT(2000, "최소 1개의 글이 있어야 합니다.", StudylogArgumentException.class),
    STUDYLOG_CONTENT_NULL_OR_EMPTY(2001, "글 내용은 공백일 수 없습나다.",
        StudylogContentNullOrEmptyException.class),
    STUDYLOG_TITLE_NULL_OR_EMPTY(2002, "글 제목은 공백일 수 없습나다.",
        StudylogTitleNullOrEmptyException.class),
    NOT_VALID_SORT_NAME(2003, "정렬 형식이 올바르지 않습니다.", NotValidSortNameException.class),
    STUDYLOG_NOT_FOUND(2004, "존재하지 않는 글입니다.", StudylogNotFoundException.class),
    STUDYLOG_TITLE_TOO_LONG(2005, String.format("스터디로그 제목이 %d자 초과입니다.", Title.MAX_LENGTH),
        TooLongTitleException.class),
    ONLY_AUTHOR_CAN_EDIT(2006, "작성자만 수정할 수 있습니다.", AuthorNotValidException.class),
    STUDYLOG_DOCUMENT_NOT_FOUND(2007, "검색용 스터디로그가 존재하지 않습니다.",
        StudylogDocumentNotFoundException.class),

    MISSION_NOT_FOUND(3000, "존재하지 않는 미션입니다.", MissionNotFoundException.class),
    DUPLICATE_MISSION(3001, "미션이 중복됩니다.", DuplicateMissionException.class),
    DUPLICATE_TAG(3002, "태그가 중복됩니다.", DuplicateTagException.class),
    TAG_NAME_NULL_OR_EMPTY(3003, "태그 이름이 null이거나 비어있습니다.", TagNameNullOrEmptyException.class),
    TOO_LONG_TAG_NAME(3004, String.format("태그 이름이 %d자 초과입니다.", TagName.MAX_LENGTH),
        TooLongTagNameException.class),
    TOO_LONG_MISSION_NAME(3005, String.format("미션 이름이 %d자 초과입니다.", Mission.MAX_LENGTH),
        TooLongMissionNameException.class),
    DUPLICATE_MEMBER_TAG(3006, "중복되는 멤버 태그 입니다.", DuplicateMemberTagException.class),
    SCRAP_ALREADY_REGISTERED(3007, "이미 스크랩한 스터디로그입니다.",
        StudylogScrapAlreadyRegisteredException.class),
    SCRAP_NOT_EXIST(3008, "스크랩이 존재하지 않습니다.", StudylogScrapNotExistException.class),
    SCRAP_NOT_VALID_USER(3009, "본인의 스크랩만 추가할 수 있습니다.", StudylogScrapNotValidUserException.class),

    INVALID_LIKE_REQUEST_EXCEPTION(5001, "스터디로그를 좋아요 할 수 없습니다.", InvalidLikeRequestException.class),
    INVALID_UNLIKE_REQUEST_EXCEPTION(5002, "스터디로그를 좋아요 취소 할 수 없습니다.",
        InvalidUnlikeRequestException.class),


    COMMENT_NOT_FOUND(6001, "존재하지 않는 댓글입니다.", CommentNotFoundException.class),
    COMMENT_DELETE_EXCEPTION(6002, "댓글을 삭제할 수 없습니다.", CommentDeleteException.class),

    INVALID_LEVEL_LOG_AUTHOR_EXCEPTION(7001, "레벨 로그 작성자가 아닙니다.",
        InvalidLevelLogAuthorException.class),
    LEVEL_LOG_NOT_FOUND_EXCEPTION(7002, "레벨 로그를 찾을 수 없습니다.", LevelLogNotFoundException.class),

    ROADMAP_KEYWORD_ORDER_EXCEPTION(8001, "키워드의 순서는 1 이상이여야 합니다.", KeywordOrderException.class),
    ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION(8002, "키워드를 찾을 수 없습니다.", KeywordNotFoundException.class),
    ROADMAP_QUIZ_NOT_FOUND_EXCEPTION(8003, "퀴즈를 찾을 수 없습니다.", QuizNotFoundException.class),
    ROADMAP_QUIZ_INVALID_QUESTION_EXCEPTION(8004, "퀴즈 내용을 업데이트할 수 없습니다.",
        QuizQuestionException.class),

    ROADMAP_KEYWORD_SAME_PARENT_EXCEPTION(8005, "부모의 키워드를 수정할 수 없습니다",
        KeywordAndKeywordParentSameException.class),

    CURRICULUM_NAME_RANGE_EXCEPTION(8006, "커리큘럼 이름은 공백일 수 없습니다", CurriculumInvalidException.class),
    CURRICULUM_NOT_FOUND_EXCEPTION(8007, "해당하는 커리큘럼을 찾을 수 없습니다", CurriculumNotFoundException.class);

    private int code;
    private String message;
    private Class<? extends BadRequestException> type;

    public static BadRequestCode findByClass(Class<?> type) {
        return Arrays.stream(BadRequestCode.values())
            .filter(code -> code.type.equals(type))
            .findAny()
            .orElseThrow(NotFoundErrorCodeException::new);
    }

}
