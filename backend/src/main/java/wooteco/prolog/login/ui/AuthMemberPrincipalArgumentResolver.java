package wooteco.prolog.login.ui;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.application.MemberService;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.excetpion.TokenNotValidException;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public class AuthMemberPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private MemberService memberService;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String credentials = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));
        Long id;
        try {
            id = Long.valueOf(jwtTokenProvider.extractSubject(credentials));
        } catch (NumberFormatException e) {
            throw new TokenNotValidException("유효하지 않은 토큰입니다.");
        }
        return memberService.findById(id);
    }
}
