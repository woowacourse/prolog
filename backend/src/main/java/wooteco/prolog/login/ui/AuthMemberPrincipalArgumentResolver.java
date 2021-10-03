package wooteco.prolog.login.ui;

import java.util.Objects;
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
<<<<<<
        AuthMemberPrincipal authMemberPrincipal = parameter
            .getParameterAnnotation(AuthMemberPrincipal.class);

        return extractTokenWhenValid(webRequest, authMemberPrincipal.required());
    }

    private Member extractTokenWhenValid(NativeWebRequest webRequest, boolean isRequired) {
        try {
            String credentials = getCredentialsIfPresent(webRequest);
            Long id = Long.valueOf(jwtTokenProvider.extractSubject(credentials));
            return memberService.findById(id);
        } catch (NumberFormatException e) {
            // subject is not ID type(long)
            return whenNotValidMember(isRequired);
        }
    }

    private String getCredentialsIfPresent(NativeWebRequest webRequest) {
        String credentials = AuthorizationExtractor
            .extract(webRequest.getNativeRequest(HttpServletRequest.class));
        if (Objects.isNull(credentials)) {
            throw new NumberFormatException();
        }
        return credentials;
    }

    private Member whenNotValidMember(boolean isRequired) {
        if (isRequired) {
            throw new TokenNotValidException();
        }
        return Member.Anonymous();
======
        String credentials = AuthorizationExtractor
            .extract(webRequest.getNativeRequest(HttpServletRequest.class));

        if (credentials == null || credentials.isEmpty()) {
            return new LoginMember(Authority.ANONYMOUS);
        }

        Long id = Long.parseLong(jwtTokenProvider.extractSubject(credentials));
        return new LoginMember(id, Authority.MEMBER);
>>>>>>> a29ed915c40f18451972297d23f47c193b176ff8
    }
}
