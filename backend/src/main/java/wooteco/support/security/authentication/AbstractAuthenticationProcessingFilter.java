package wooteco.support.security.authentication;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;
import wooteco.support.security.context.SecurityContextHolder;
import wooteco.support.security.exception.AuthenticationException;

@AllArgsConstructor
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean {

    protected AuthenticationSuccessHandler successHandler;
    protected AuthenticationFailureHandler failureHandler;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);

            return;
        }

        try {
            Authentication authResult = attemptAuthentication(request, response);
            successfulAuthentication(request, response, chain, authResult);
        } catch (AuthenticationException failed) {
            unsuccessfulAuthentication(request, response, failed);
            return;
        }
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
        throws IOException {

        SecurityContextHolder.getContext().setAuthentication(authResult);

        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) {
        SecurityContextHolder.clearContext();

        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    public abstract Authentication attemptAuthentication(HttpServletRequest request,
                                                         HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException;

    protected abstract boolean requiresAuthentication(HttpServletRequest request,
                                                      HttpServletResponse response);
}
