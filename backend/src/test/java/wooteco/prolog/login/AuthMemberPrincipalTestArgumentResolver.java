package wooteco.prolog.login;

import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.domain.Member;
import wooteco.prolog.login.ui.AuthMemberPrincipalArgumentResolver;

import static wooteco.prolog.Documentation.MEMBER1;

@Profile("test")
@Component
public class AuthMemberPrincipalTestArgumentResolver implements AuthMemberPrincipalArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return MEMBER1;
    }
}
