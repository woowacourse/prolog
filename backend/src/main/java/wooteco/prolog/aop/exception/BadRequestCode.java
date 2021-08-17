package wooteco.prolog.aop.exception;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wooteco.prolog.login.excetpion.GithubApiFailException;
import wooteco.prolog.login.excetpion.GithubConnectionException;
import wooteco.prolog.login.excetpion.PostTitleNullOrEmptyException;
import wooteco.prolog.login.excetpion.RoleNameNotFoundException;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.exception.MemberNotAllowedException;
import wooteco.prolog.member.exception.MemberNotFoundException;
import wooteco.prolog.mission.exception.DuplicateMissionException;
import wooteco.prolog.mission.exception.MissionNotFoundException;
import wooteco.prolog.post.exception.NotValidSortNameException;
import wooteco.prolog.post.exception.PostArgumentException;
import wooteco.prolog.post.exception.PostContentNullOrEmptyException;
import wooteco.prolog.post.exception.PostNotFoundException;
import wooteco.prolog.tag.exception.DuplicateTagException;

@AllArgsConstructor
@Getter
public enum BadRequestCode {
    NOT_FOUND_ERROR_CODE(0000, "해당 에러의 에러코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),

    GITHUB_API_FAIL(1000, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다.", GithubApiFailException.class),
    GITHUB_CONNECTION_FAIL(1001, "깃헙 API에서 엑세스 토큰을 받아오는 데 실패했습니다.", GithubConnectionException.class),
    TOKEN_NOT_VALID(1002, "JWT 토큰이 유효하지 않습니다.", TokenNotValidException.class),
    ROLE_NAME_NOT_FOUND(1003, "해당 이름을 가진 역할이 없습니다.", RoleNameNotFoundException.class),
    MEMBER_NOT_FOUND(1004, "해당 ID를 가진 멤버가 없습니다.", MemberNotFoundException.class),
    MEMBER_NOT_ALLOWED(1005, "권한이 없습니다.", MemberNotAllowedException.class),

    POST_ARGUMENT(2000, "최소 1개의 글이 있어야 합니다.", PostArgumentException.class),
    POST_CONTENT_NULL_OR_EMPTY(2001, "글 내용은 공백일 수 없습나다.", PostContentNullOrEmptyException.class),
    POST_TITLE_NULL_OR_EMPTY(2002, "글 제목은 공백일 수 없습나다.", PostTitleNullOrEmptyException.class),
    NOT_VALID_SORT_NAME(2003, "정렬 형식이 올바르지 않습니다.", NotValidSortNameException.class),
    POST_NOT_FOUND(2004, "존재하지 않는 글입니다.", PostNotFoundException.class),

    MISSION_NOT_FOUND(3000, "존재하지 않는 미션입니다.", MissionNotFoundException.class),
    DUPLICATE_MISSION(3001, "미션이 중복됩니다.", DuplicateMissionException.class),
    DUPLICATE_TAG(3002, "태그가 중복됩니다.", DuplicateTagException.class)
    ;

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
