package wooteco.prolog.login.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import wooteco.prolog.login.ui.LoginMember;
import wooteco.prolog.member.exception.MemberNotAllowedException;

@Aspect
@Component
public class LoginMemberVerifier {

    @Before("@annotation(wooteco.prolog.login.aop.MemberOnly)")
    public void checkLoginMember(JoinPoint joinPoint) {
        final LoginMember loginMember = (LoginMember) Arrays.stream(joinPoint.getArgs())
            .filter(argument -> argument instanceof LoginMember)
            .findAny()
            .orElseThrow(() -> new IllegalStateException("LoginMember 가 존재하지 않습니다."));

        loginMember.act()
            .throwIfAnonymous(MemberNotAllowedException::new);
    }
}
