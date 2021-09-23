package wooteco.support.security.access.matcher;

import javax.servlet.http.HttpServletRequest;

public class AnyRequestMatcher implements RequestMatcher {

    public static final RequestMatcher INSTANCE = new AnyRequestMatcher();

    @Override
    public boolean matches(HttpServletRequest request) {
        return true;
    }
}
