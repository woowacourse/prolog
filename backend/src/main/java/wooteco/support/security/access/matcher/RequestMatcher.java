package wooteco.support.security.access.matcher;

import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {

    boolean matches(HttpServletRequest request);
}
