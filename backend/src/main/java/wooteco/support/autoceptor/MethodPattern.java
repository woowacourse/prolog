package wooteco.support.autoceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import org.springframework.http.HttpMethod;

public class MethodPattern {

    private static final String INVALID_METHOD_PATTERN_MESSAGE = "uri 와 method 가 지정되지 않은 MethodPattern 이 있습니다. method : %s, uri : %s";

    private final HttpMethod method;
    private final Pattern pattern;

    public MethodPattern(HttpMethod method, String uri) {
        validate(uri, method);
        this.method = method;
        this.pattern = convertToPattern(uri);
    }

    private Pattern convertToPattern(String uri) {
        String replace = uri.replace("*", "[^/]+");
        String regex = "^" + replace + "$";
        return Pattern.compile(regex);
    }

    private void validate(String method, HttpMethod uri) {
        if (uri == null || method == null) {
            throw new IllegalArgumentException(String.format(INVALID_METHOD_PATTERN_MESSAGE, method, uri));
        }
    }

    public boolean match(HttpServletRequest request) {
        if (!pattern.matcher(request.getRequestURI()).matches()) {
            return false;
        }
        return method.matches(request.getMethod());
    }
}
