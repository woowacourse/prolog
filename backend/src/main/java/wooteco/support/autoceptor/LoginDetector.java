package wooteco.support.autoceptor;


import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class LoginDetector {

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
