package wooteco.support.autoceptor;


import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class AuthenticationDetector {

    private static final String ORIGIN = "Origin";
    private static final String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private final List<MethodPattern> requireLoginPatterns;

    public boolean requireLogin(HttpServletRequest request) {
        if (isPreflighted(request)) {
            return false;
        }

        if (HttpMethod.GET.matches(request.getMethod())) {
            return false;
        }

        return requireLoginPatterns.stream()
            .anyMatch(pattern -> pattern.match(request));
    }

    private boolean isPreflighted(HttpServletRequest request) {
        return isOptionsMethod(request)
            && hasOrigin(request)
            && hasRequestHeaders(request)
            && hasRequestMethods(request);
    }

    private boolean isOptionsMethod(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    private boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ORIGIN));
    }

    private boolean hasRequestMethods(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_METHOD));
    }

    private boolean hasRequestHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_HEADERS));
    }
}
