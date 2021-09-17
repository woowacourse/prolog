package wooteco.prolog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.prolog.login.application.dto.TokenRequest;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException {
        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return;
            }

            TokenRequest tokenRequest = new ObjectMapper()
                .readValue(request.getReader(), TokenRequest.class);

            successHandler.onAuthenticationSuccess(request, response, () -> tokenRequest);

        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } catch (IOException e) {
            failureHandler
                .onAuthenticationFailure(request, response, new AuthenticationException());
        }

    }
}
