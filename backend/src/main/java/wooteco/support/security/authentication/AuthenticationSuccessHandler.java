package wooteco.support.security.authentication;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationSuccessHandler {

    void onAuthenticationSuccess(HttpServletRequest request,
                                 HttpServletResponse response, OAuth2Authentication authentication)
        throws IOException;
}
