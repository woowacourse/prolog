package wooteco.prolog.login.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import wooteco.prolog.login.application.AuthorizationExtractor;
import wooteco.prolog.login.application.GithubLoginService;

@Profile("!docu")
@Component
@AllArgsConstructor
public class LoginInterceptorImpl implements LoginInterceptor {

    private static final String ORIGIN = "Origin";
    private static final String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private final GithubLoginService githubLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isPreflighted(request)) {
            return true;
        }

        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        githubLoginService.validateToken(AuthorizationExtractor.extract(request));
        return true;
    }

    private boolean isPreflighted(HttpServletRequest request) {
        return isOptionsMethod(request)
                && hasOrigin(request)
                && hasRequestHeaders(request)
                && hasRequestMethods(request);
    }

    public boolean isOptionsMethod(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    public boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ORIGIN));
    }

    public boolean hasRequestMethods(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_METHOD));
    }

    public boolean hasRequestHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_HEADERS));
    }
}
