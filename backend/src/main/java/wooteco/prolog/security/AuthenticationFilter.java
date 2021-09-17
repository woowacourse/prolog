package wooteco.prolog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.prolog.login.application.dto.OAuth2AuthorizationGrantRequest;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return;
            }

            OAuth2AuthorizationGrantRequest oAuth2AuthorizationGrantRequest
                = new ObjectMapper()
                .readValue(request.getReader(), OAuth2AuthorizationGrantRequest.class);

            OAuth2AccessTokenResponse githubAccessToken = authenticationProvider
                .authenticate(oAuth2AuthorizationGrantRequest);

            successHandler.onAuthenticationSuccess(request, response, githubAccessToken);

        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } catch (IOException e) {
            failureHandler
                .onAuthenticationFailure(request, response, new AuthenticationException());
        }

    }
}
