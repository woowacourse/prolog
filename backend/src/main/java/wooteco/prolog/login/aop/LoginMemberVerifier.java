package wooteco.prolog.login.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.ui.LoginMember.Authority;

import static wooteco.prolog.common.exception.BadRequestCode.MEMBER_NOT_ALLOWED;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginMemberVerifier {

    private final MemberAuthorityCache memberAuthorityCache;

    @Before("@annotation(wooteco.prolog.login.aop.MemberOnly)")
    public void checkLoginMember() {
        final Authority authority = memberAuthorityCache.getAuthority();
        if (!authority.equals(Authority.MEMBER)) {
            throw new BadRequestException(MEMBER_NOT_ALLOWED);
        }
    }

}
