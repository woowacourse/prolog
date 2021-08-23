package wooteco.prolog.login.ui;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@AllArgsConstructor
@Profile("!docu")
@Component
public class AuthMemberPrincipalArgumentResolverImpl implements
    AuthMemberPrincipalArgumentResolver {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String credentials = AuthorizationExtractor
            .extract(webRequest.getNativeRequest(HttpServletRequest.class));
        try {
            Long id = Long.valueOf(jwtTokenProvider.extractSubject(credentials));
            return memberService.findById(id);
        } catch (NumberFormatException e) {
            throw new TokenNotValidException();
        }
    }
}
