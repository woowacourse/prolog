package wooteco.prolog.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.session.domain.Mission;
import wooteco.prolog.session.domain.Session;
import wooteco.prolog.studylog.domain.TagName;
import wooteco.prolog.studylog.domain.Title;

@AllArgsConstructor
@Getter
public enum BadRequestCode {
    NOT_FOUND_ERROR_CODE(0000, "해당 에러의 에러코드를 찾을 수 없습니다."),

    GITHUB_API_FAIL(1000, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다."),
    GITHUB_CONNECTION_FAIL(1001, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다."),
    TOKEN_NOT_VALID(1002, "JWT 토큰이 유효하지 않습니다."),
    ROLE_NAME_NOT_FOUND(1003, "해당 이름을 가진 역할이 없습니다."),
    MEMBER_NOT_FOUND(1004, "해당 ID를 가진 멤버가 없습니다."),
    MEMBER_NOT_ALLOWED(1005, "권한이 없습니다."),

    STUDYLOG_ARGUMENT(2000, "최소 1개의 글이 있어야 합니다."),
    STUDYLOG_CONTENT_NULL_OR_EMPTY(2001, "글 내용은 공백일 수 없습니다."),
    STUDYLOG_TITLE_NULL_OR_EMPTY(2002, "글 제목은 공백일 수 없습니다."),
    NOT_VALID_SORT_NAME(2003, "정렬 형식이 올바르지 않습니다."),
    STUDYLOG_NOT_FOUND(2004, "존재하지 않는 글입니다."),
    STUDYLOG_TITLE_TOO_LONG(2005, String.format("스터디로그 제목이 %d자 초과입니다.", Title.MAX_LENGTH)),
    ONLY_AUTHOR_CAN_EDIT(2006, "작성자만 수정할 수 있습니다."),
    STUDYLOG_DOCUMENT_NOT_FOUND(2007, "검색용 스터디로그가 존재하지 않습니다."),
    STUDYLOG_SCRAP_ALREADY_REGISTERED_EXCEPTION(2008, "이미 등록한 스터디 로그 스크랩입니다."),
    STUDYLOG_SCRAP_NOT_EXIST_EXCEPTION(2009, "존재하지 않는 스터디 로그 스크랩입니다."),

    MISSION_NOT_FOUND(3000, "존재하지 않는 미션입니다."),
    DUPLICATE_MISSION(3001, "미션이 중복됩니다."),
    DUPLICATE_TAG(3002, "태그가 중복됩니다."),
    TAG_NAME_NULL_OR_EMPTY(3003, "태그 이름이 null이거나 비어있습니다."),
    TOO_LONG_TAG_NAME(3004, String.format("태그 이름이 %d자 초과입니다.", TagName.MAX_LENGTH)),
    TOO_LONG_MISSION_NAME(3005, String.format("미션 이름이 %d자 초과입니다.", Mission.MAX_LENGTH)),
    DUPLICATE_MEMBER_TAG(3006, "중복되는 멤버 태그 입니다."),
    SCRAP_ALREADY_REGISTERED(3007, "이미 스크랩한 스터디로그입니다."),
    SCRAP_NOT_EXIST(3008, "스크랩이 존재하지 않습니다."),
    SCRAP_NOT_VALID_USER(3009, "본인의 스크랩만 추가할 수 있습니다."),
    NOT_EXISTS_MEMBER_TAG(3010, "멤버 태그가 존재하지 않습니다."),
    CANT_FIND_GROUP_TYPE(3011, "해당 그룹의 타입을 결정할 수 없습니다."),

    INVALID_LIKE_REQUEST_EXCEPTION(5001, "스터디로그를 좋아요 할 수 없습니다."),
    INVALID_UNLIKE_REQUEST_EXCEPTION(5002, "스터디로그를 좋아요 취소 할 수 없습니다."),

    COMMENT_NOT_FOUND(6001, "존재하지 않는 댓글입니다."),
    COMMENT_DELETE_EXCEPTION(6002, "댓글을 삭제할 수 없습니다."),

    INVALID_LEVEL_LOG_AUTHOR_EXCEPTION(7001, "레벨 로그 작성자가 아닙니다."),
    LEVEL_LOG_NOT_FOUND_EXCEPTION(7002, "레벨 로그를 찾을 수 없습니다."),
    SELF_DISCUSSION_NOT_FOUND_EXCEPTION(7003, "자신이 작성한 디스커션을 찾을 수 없습니다."),

    ROADMAP_KEYWORD_ORDER_EXCEPTION(8001, "키워드의 순서는 1 이상이여야 합니다."),
    ROADMAP_KEYWORD_NOT_FOUND_EXCEPTION(8002, "키워드를 찾을 수 없습니다."),
    ROADMAP_QUIZ_NOT_FOUND_EXCEPTION(8003, "퀴즈를 찾을 수 없습니다."),
    ROADMAP_QUIZ_INVALID_QUESTION_EXCEPTION(8004, "퀴즈 내용을 업데이트할 수 없습니다."),
    ROADMAP_KEYWORD_SAME_PARENT_EXCEPTION(8005, "부모의 키워드를 수정할 수 없습니다"),
    ROADMAP_SESSION_NOT_FOUND_EXCEPTION(8006, "세션을 찾을 수 없습니다."),
    ROADMAP_KEYWORD_SEQUENCE_EXCEPTION(8007, "키워드 시퀀스가 유효하지 않습니다."),
    ROADMAP_KEYWORD_AND_KEYWORD_PARENT_SAME_EXCEPTION(8008, "로드맵 키워드는 키워드의 부모와 같을 수 없습니다."),

    CURRICULUM_NAME_RANGE_EXCEPTION(8009, "커리큘럼 이름은 공백일 수 없습니다"),
    CURRICULUM_NOT_FOUND_EXCEPTION(8010, "해당하는 커리큘럼을 찾을 수 없습니다"),
    CURRICULUM_INVALID_EXCEPTION(8011, "커리큘럼이 유효하지 않습니다."),

    ESSAY_ANSWER_NOT_FOUND_EXCEPTION(8012, "해당 답변을 찾을 수 없습니다."),
    NOT_EMPTY_ESSAY_ANSWER_EXCEPTION(8013, "답변은 공백일 수 없습니다."),
    ESSAY_ANSWER_NOT_VALID_USER(8014, "본인이 작성한 답변만 수정할 수 있습니다."),

    ROADMAP_RECOMMENDED_POST_NOT_FOUND(8101, "해당 추천 포스트가 존재하지 않습니다."),

    FILE_NAME_EMPTY_EXCEPTION(9001, "파일 이름이 존재하지 않습니다."),
    UNSUPPORTED_FILE_EXTENSION_EXCEPTION(9002, "지원하지 않는 파일 확장자입니다."),
    FILE_UPLOAD_FAIL_EXCEPTION(9003, "파일 업로드에 실패했습니다."),

    DUPLICATE_SESSION_EXCEPTION(10001, "중복되는 세션입니다."),
    TOO_LONG_LEVEL_NAME_EXCEPTION(10003, String.format("세션 이름이 %d자 초과입니다.", Session.MAX_LENGTH)),

    SEARCH_ARGUMENT_PARSE_EXCEPTION(11001, "parsing 할 수 없는 argument입니다."),

    ARTICLE_URL_OVER_LENGTH_EXCEPTION(12000, "ARTICLE_URL_OVER_LENGTH_EXCEPTION"),
    ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION(12001, "ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION"),
    ARTICLE_TITLE_OVER_LENGTH_EXCEPTION(12002, "ARTICLE_TITLE_OVER_LENGTH_EXCEPTION"),
    ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION(12003, "ARTICLE_URL_NULL_OR_EMPTY_EXCEPTION"),
    ARTICLE_NOT_FOUND_EXCEPTION(12004, "ARTICLE_NOT_FOUND_EXCEPTION"),
    INVALID_ARTICLE_AUTHOR_EXCEPTION(12005, "INVALID_ARTICLE_AUTHOR_EXCEPTION");

    private int code;
    private String message;
}
