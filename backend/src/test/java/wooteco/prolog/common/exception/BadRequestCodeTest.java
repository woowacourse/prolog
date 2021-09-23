package wooteco.prolog.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.member.exception.MemberNotFoundException;

class BadRequestCodeTest {

    @DisplayName("예외 코드와 메시지가 잘 바인딩되는지 확인한다.")
    @Test
    void findByTest() {
        //given
        MemberNotFoundException memberNotFoundException = new MemberNotFoundException();
        GithubApiFailException githubApiFailException = new GithubApiFailException();

        //when
        int githubApiFailExceptionCode = githubApiFailException.getCode();
        String githubApiFailExceptionMessage = githubApiFailException.getMessage();

        int memberNotFoundExceptionCode = memberNotFoundException.getCode();
        String memberNotFoundExceptionMessage = memberNotFoundException.getMessage();

        //then
        assertThat(githubApiFailExceptionCode).isEqualTo(BadRequestCode.GITHUB_API_FAIL.getCode());
        assertThat(githubApiFailExceptionMessage)
            .isEqualTo(BadRequestCode.GITHUB_API_FAIL.getMessage());

        assertThat(memberNotFoundExceptionCode)
            .isEqualTo(BadRequestCode.MEMBER_NOT_FOUND.getCode());
        assertThat(memberNotFoundExceptionMessage)
            .isEqualTo(BadRequestCode.MEMBER_NOT_FOUND.getMessage());
    }

    @DisplayName("해당하는 예외가 없을 때 오류가 나는지 확인한다.")
    @Test
    void failFoundExceptionTest() {
        //given
        //when
        //then
        assertThatThrownBy(BadRequestException::new)
            .isInstanceOf(NotFoundErrorCodeException.class);
    }
}
