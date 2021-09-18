package wooteco.support.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.support.security.client.ClientRegistration;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.context.SecurityContext;
import wooteco.support.security.context.SecurityContextHolder;
import wooteco.support.security.exception.AuthenticationException;
import wooteco.support.security.oauth2user.OAuth2AuthorizationGrantRequest;

@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            ClientRegistration clientRegistration =
                clientRegistrationRepository.findByRegistrationId("github");
            OAuth2AuthorizationExchange exchange= new ObjectMapper()
                .readValue(request.getReader(), OAuth2AuthorizationExchange.class);

            OAuth2AuthorizationGrantRequest oAuth2AuthorizationGrantRequest
                = new OAuth2AuthorizationGrantRequest(clientRegistration, exchange);

            OAuth2Authentication authentication = authenticationProvider
                .authenticate(oAuth2AuthorizationGrantRequest);

            successfulAuthentication(request, response, filterChain, authentication);

        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        } catch (IOException e) {
            failureHandler
                .onAuthenticationFailure(request, response, new AuthenticationException());
        }
    }
    private void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          OAuth2Authentication authentication) throws IOException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        successHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
