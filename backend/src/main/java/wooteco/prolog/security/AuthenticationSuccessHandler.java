package wooteco.prolog.security;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationSuccessHandler {

    void onAuthenticationSuccess(HttpServletRequest request,
                                 HttpServletResponse response, Authentication authentication)
        throws IOException;
}
