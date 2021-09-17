package wooteco.prolog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationFailureHandler {

    void onAuthenticationFailure(HttpServletRequest request,
                                 HttpServletResponse response, AuthenticationException exception);
}
