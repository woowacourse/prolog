package wooteco.support.security.authorization.requestmatcher;

import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {

    boolean matches(HttpServletRequest request);
}
