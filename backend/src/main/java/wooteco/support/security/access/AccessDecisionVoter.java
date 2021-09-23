package wooteco.support.security.access;

import static wooteco.support.security.config.configurer.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl.AUTHENTICATED;
import static wooteco.support.security.config.configurer.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl.PERMIT_ALL;

import java.util.Collection;
import wooteco.support.security.authentication.Authentication;

public class AccessDecisionVoter {

    static final int ACCESS_GRANTED = 1;
    static final int ACCESS_ABSTAIN = 0;
    static final int ACCESS_DENIED = -1;

    public int vote(Authentication authentication, FilterInvocation fi,
                    Collection<ConfigAttribute> attributes) {

        boolean permitAll = attributes.stream()
            .filter(it -> it.getAttribute().equalsIgnoreCase(PERMIT_ALL))
            .findAny()
            .isPresent();
        if (permitAll) {
            return ACCESS_GRANTED;
        }

        boolean authenticated = attributes.stream()
            .filter(it -> it.getAttribute().equalsIgnoreCase(AUTHENTICATED))
            .findAny()
            .isPresent();
        if (authenticated) {
            return authentication == null ? ACCESS_DENIED : ACCESS_GRANTED;
        }
        throw new RuntimeException();
    }

}
