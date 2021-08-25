package wooteco.prolog;

import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.login.domain.AuthMemberPrincipal;
import wooteco.prolog.login.ui.AuthMemberPrincipalArgumentResolver;
import wooteco.prolog.member.domain.Member;
import wooteco.prolog.member.domain.Role;

@Profile("docu")
@Component
public class AuthMemberPrincipalTestArgumentResolver implements AuthMemberPrincipalArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMemberPrincipal.class);
    }

    @Override
    public Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader("Authorization");
        if (header.contains("Bearer")) {
            return new Member(
                    1L,
                    GithubResponses.소롱.getLogin(),
                    GithubResponses.소롱.getName(),
                    Role.CREW,
                    Long.parseLong(GithubResponses.소롱.getId()),
                    GithubResponses.소롱.getAvatarUrl()
            );
        }
        return new Member(1L, "githubUserName", "nickname", Role.CREW, 1L, "https://avatars.githubusercontent.com/u/52682603?v=4");
    }
}
