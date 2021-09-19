package wooteco.support.security.authentication;

import java.util.List;
import lombok.AllArgsConstructor;
import wooteco.support.security.exception.AuthenticationException;

@AllArgsConstructor
public class ProviderManager implements AuthenticationManager {

    private List<AuthenticationProvider> providers;

    @Override
    public Authentication authenticate(AuthenticationRequest authenticationRequest) {
        for (AuthenticationProvider provider : providers) {
            if (!provider.supports(authenticationRequest.getClass())) {
                continue;
            }
            return provider.authenticate(authenticationRequest);
        }
        throw new AuthenticationException();
    }
}
