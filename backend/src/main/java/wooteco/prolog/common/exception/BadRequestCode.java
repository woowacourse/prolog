package wooteco.prolog.common.exception;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.login.excetpion.GithubApiFailException;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.login.excetpion.RoleNameNotFoundException;
import wooteco.prolog.login.excetpion.StudylogTitleNullOrEmptyException;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.exception.DuplicateMemberTagException;
import wooteco.prolog.member.exception.MemberNotAllowedException;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.report.exception.DefaultAbilityNotFoundException;
import wooteco.prolog.report.exception.GraphAbilitiesAreNotParentException;
import wooteco.prolog.report.exception.ReportNotFoundException;
import wooteco.prolog.report.exception.ReportRequestTypeException;
import wooteco.prolog.report.exception.ReportUpdateException;
import wooteco.prolog.studylog.exception.DuplicateReportTitleException;
import wooteco.prolog.studylog.exception.StudylogScrapAlreadyRegisteredException;
import wooteco.prolog.studylog.exception.StudylogScrapNotExistException;
import wooteco.prolog.studylog.exception.StudylogScrapNotValidUserException;
import wooteco.prolog.report.exception.AbilityHasChildrenException;
import wooteco.prolog.report.exception.AbilityNotFoundException;
import wooteco.prolog.report.exception.AbilityParentChildColorDifferentException;
import wooteco.prolog.studylog.domain.Mission;
import wooteco.prolog.studylog.domain.TagName;
import wooteco.prolog.studylog.domain.Title;
import wooteco.prolog.studylog.exception.AbilityParentColorDuplicateException;
import wooteco.prolog.studylog.exception.AbilityNameDuplicateException;
import wooteco.prolog.studylog.exception.AuthorNotValidException;
import wooteco.prolog.studylog.exception.DuplicateMissionException;
import wooteco.prolog.studylog.exception.DuplicateTagException;
import wooteco.prolog.studylog.exception.MissionNotFoundException;
import wooteco.prolog.studylog.exception.NotValidSortNameException;
import wooteco.prolog.studylog.exception.StudylogArgumentException;
import wooteco.prolog.studylog.exception.StudylogContentNullOrEmptyException;
import wooteco.prolog.studylog.exception.StudylogDocumentNotFoundException;
import wooteco.prolog.studylog.exception.StudylogNotFoundException;
import wooteco.prolog.studylog.exception.TagNameNullOrEmptyException;
import wooteco.prolog.studylog.exception.TooLongMissionNameException;
import wooteco.prolog.studylog.exception.TooLongTagNameException;
import wooteco.prolog.studylog.exception.TooLongTitleException;

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
    SCRAP_ALREADY_REGISTERED(3007, "이미 스크랩한 스터디로그입니다.", StudylogScrapAlreadyRegisteredException.class),
    SCRAP_NOT_EXIST(3008, "스크랩이 존재하지 않습니다.", StudylogScrapNotExistException.class),
    SCRAP_NOT_VALID_USER(3009, "본인의 스크랩만 추가할 수 있습니다.", StudylogScrapNotValidUserException.class),

    ABILITY_NOT_FOUND(4000, "역량이 존재하지 않습니다.", AbilityNotFoundException.class),
    ABILITY_HAS_CHILDREN(4001, "해당 역량의 하위 역량이 존재합니다.", AbilityHasChildrenException.class),
    ABILITY_NAME_DUPLICATE(4002, "중복된 이름의 역량이 존재합니다.", AbilityNameDuplicateException.class),
    ABILITY_PARENT_COLOR_DUPLICATE(4003, "중복된 색상의 부모역량이 존재합니다.",
                                   AbilityParentColorDuplicateException.class),
    ABILITY_PARENT_CHILD_COLOR_DIFFERENT(4004, "상위 역량과 하위 역량의 색상이 일치하지 않습니다.",
                                         AbilityParentChildColorDifferentException.class),
    DUPLICATE_REPORT_TITLE(4005, "리포트의 이름은 중복일 수 없습니다.", DuplicateReportTitleException.class),
    DEFAULT_ABILITY_NOT_FOUND(4006, "입력된 과정의 기본 역량이 존재하지 않습니다.", DefaultAbilityNotFoundException.class),
    GRAPH_ABILITIES_ARE_NOT_PARENT_EXCEPTION(4007, "그래프의 역량은 부모 역량만 등록 가능합니다.",
        GraphAbilitiesAreNotParentException.class),
    REPORT_NOT_FOUND_EXCEPTION(4008, "리포트를 찾을 수 없습니다", ReportNotFoundException.class),
    REPORT_REQUEST_TYPE_EXCEPTION(4009, "리포트 검색 타입을 찾을 수 없습니다.", ReportRequestTypeException.class),
    REPORT_UPDATE_EXCEPTION(4010, "리포트를 업데이트 하는데 실패했습니다.", ReportUpdateException.class);


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
