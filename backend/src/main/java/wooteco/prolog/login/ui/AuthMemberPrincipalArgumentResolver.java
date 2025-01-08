package wooteco.prolog.login.ui;

import static wooteco.prolog.common.exception.BadRequestCode.TOKEN_NOT_VALID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.login.aop.MemberAuthorityCache;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.LoginMember.Authority;

@AllArgsConstructor
public class AuthMemberPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberAuthorityCache memberAuthorityCache;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public LoginMember resolveArgument(MethodParameter parameter,
                                       ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory) {
        String credentials = AuthorizationExtractor
            .extract(webRequest.getNativeRequest(HttpServletRequest.class));

        if (credentials == null || credentials.isEmpty()) {
            memberAuthorityCache.setAuthority(Authority.ANONYMOUS);
            return new LoginMember(Authority.ANONYMOUS);
        }

        try {
            Long id = Long.parseLong(jwtTokenProvider.extractSubject(credentials));
            memberAuthorityCache.setAuthority(Authority.MEMBER);
            return new LoginMember(id, Authority.MEMBER);
        } catch (NumberFormatException e) {
            throw new BadRequestException(TOKEN_NOT_VALID);
        }
    }

}
