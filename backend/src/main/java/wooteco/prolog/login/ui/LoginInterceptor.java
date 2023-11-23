package wooteco.prolog.login.ui;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.GithubLoginService;
import wooteco.support.autoceptor.AuthenticationDetector;

@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final GithubLoginService githubLoginService;
    private final AuthenticationDetector authenticationDetector;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!authenticationDetector.requireLogin(request)) {
            return true;
        }

        githubLoginService.validateToken(AuthorizationExtractor.extract(request));
        return true;
    }
}
