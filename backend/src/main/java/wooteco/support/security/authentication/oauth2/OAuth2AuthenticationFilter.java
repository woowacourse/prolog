package wooteco.support.security.authentication.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import wooteco.support.security.authentication.AbstractAuthenticationProcessingFilter;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.authentication.AuthenticationFailureHandler;
import wooteco.support.security.authentication.AuthenticationManager;
import wooteco.support.security.authentication.AuthenticationSuccessHandler;
import wooteco.support.security.client.ClientRegistration;
import wooteco.support.security.client.ClientRegistrationRepository;
import wooteco.support.security.exception.AuthenticationException;
import wooteco.support.security.oauth2user.OAuth2AuthorizationRequest;

public class OAuth2AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthenticationManager authenticationManager;

    public OAuth2AuthenticationFilter(ClientRegistrationRepository clientRegistrationRepository,
                                      AuthenticationManager authenticationManager,
                                      AuthenticationSuccessHandler successHandler,
                                      AuthenticationFailureHandler failureHandler) {
        super(successHandler, failureHandler);
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
        throws AuthenticationException, IOException {
        ClientRegistration clientRegistration =
            clientRegistrationRepository.findByRegistrationId("github");

        OAuth2AuthorizationExchange exchange = new ObjectMapper()
            .readValue(request.getReader(), OAuth2AuthorizationExchange.class);

        OAuth2AuthorizationRequest oAuth2AuthorizationRequest
            = new OAuth2AuthorizationRequest(clientRegistration, exchange);

        return authenticationManager.authenticate(oAuth2AuthorizationRequest);
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return true;
    }
}
