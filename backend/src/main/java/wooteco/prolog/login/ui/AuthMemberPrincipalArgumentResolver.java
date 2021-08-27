package wooteco.prolog.login.ui;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wooteco.prolog.member.domain.Member;

public interface AuthMemberPrincipalArgumentResolver extends HandlerMethodArgumentResolver {

    boolean supportsParameter(MethodParameter parameter);

    Member resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                           NativeWebRequest webRequest, WebDataBinderFactory binderFactory);
}
