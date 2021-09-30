package wooteco.prolog.login.ui;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember.Authority;

@AllArgsConstructor
@Component
public class AuthMemberPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public LoginMember resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String credentials = AuthorizationExtractor
            .extract(webRequest.getNativeRequest(HttpServletRequest.class));

        if (!jwtTokenProvider.validateToken(credentials)) {
            return new LoginMember(Authority.ANONYMOUS);
        }

        Long id = Long.parseLong(jwtTokenProvider.extractSubject(credentials));
        return new LoginMember(id, Authority.MEMBER);
    }
}
