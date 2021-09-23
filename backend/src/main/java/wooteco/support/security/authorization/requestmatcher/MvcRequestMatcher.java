package wooteco.support.security.authorization.requestmatcher;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpMethod;

@AllArgsConstructor
@Getter
public class MvcRequestMatcher implements RequestMatcher {

    private HttpMethod method;
    private String pattern;

    public boolean matches(HttpServletRequest request) {
        if (this.method != null && !this.method.name().equals(request.getMethod())) {
            return false;
        }

        return request.getRequestURI().contains(pattern);
    }
}
