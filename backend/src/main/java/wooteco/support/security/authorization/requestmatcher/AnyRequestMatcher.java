package wooteco.support.security.authorization.requestmatcher;

import javax.servlet.http.HttpServletRequest;

public class AnyRequestMatcher implements RequestMatcher {

    public static final RequestMatcher INSTANCE = new AnyRequestMatcher();

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }
}
