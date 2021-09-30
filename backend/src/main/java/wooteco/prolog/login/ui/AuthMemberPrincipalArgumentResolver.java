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
    }
}
