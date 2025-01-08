package wooteco.prolog.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static wooteco.prolog.common.exception.BadRequestCode.GITHUB_API_FAIL;
import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_FOUND;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BadRequestCodeTest {

    @DisplayName("예외 코드와 메시지가 잘 바인딩되는지 확인한다.")
    @Test
    void findByTest() {
        //given
        BadRequestException memberNotFoundException = new BadRequestException(MEMBER_NOT_FOUND);
        BadRequestException githubApiFailException = new BadRequestException(GITHUB_API_FAIL);

        //when
        int githubApiFailExceptionCode = githubApiFailException.getCode();
        String githubApiFailExceptionMessage = githubApiFailException.getMessage();

        int memberNotFoundExceptionCode = memberNotFoundException.getCode();
        String memberNotFoundExceptionMessage = memberNotFoundException.getMessage();

        //then
        assertThat(githubApiFailExceptionCode).isEqualTo(GITHUB_API_FAIL.getCode());
        assertThat(githubApiFailExceptionMessage)
            .isEqualTo(GITHUB_API_FAIL.getMessage());

        assertThat(memberNotFoundExceptionCode)
            .isEqualTo(MEMBER_NOT_FOUND.getCode());
        assertThat(memberNotFoundExceptionMessage)
            .isEqualTo(MEMBER_NOT_FOUND.getMessage());
    }

}
