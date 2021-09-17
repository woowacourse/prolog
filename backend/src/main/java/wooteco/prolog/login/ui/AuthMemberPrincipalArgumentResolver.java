package wooteco.prolog.login.ui;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.security.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.application.MemberService;
import wooteco.prolog.member.domain.Member;

@AllArgsConstructor
@Component
public class AuthMemberPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

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
