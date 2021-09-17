package wooteco.prolog.login.ui;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import wooteco.prolog.security.AuthorizationExtractor;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.excetpion.TokenNotValidException;

@Component
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private static final String ORIGIN = "Origin";
    private static final String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (isPreflighted(request)) {
            return true;
        }

        if (HttpMethod.GET.matches(request.getMethod())) {
            return true;
        }

        if (!jwtTokenProvider.validateToken(AuthorizationExtractor.extract(request))) {
            throw new TokenNotValidException();
        }
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
