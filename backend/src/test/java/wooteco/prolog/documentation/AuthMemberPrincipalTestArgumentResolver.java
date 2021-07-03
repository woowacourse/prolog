package wooteco.prolog.documentation;

import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;
import wooteco.prolog.login.ui.AuthMemberPrincipalArgumentResolver;

@Profile("docu")
@Component
public class AuthMemberPrincipalTestArgumentResolver implements AuthMemberPrincipalArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return new Member(1L, "githubUserName", "nickname", Role.CREW, 1L, "https://avatars.githubusercontent.com/u/52682603?v=4");
    }
}
