package wooteco.support.security.authorization;

import static wooteco.support.security.authorization.AccessDecisionVoter.ACCESS_GRANTED;

import java.util.Collection;
import lombok.AllArgsConstructor;
import wooteco.support.security.authentication.Authentication;

@AllArgsConstructor
public class AccessDecisionManager {

    private AccessDecisionVoter decisionVoter;

    public void decide(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> configAttributes) {

        int result = decisionVoter.vote(authentication, fi, configAttributes);

        if (result < ACCESS_GRANTED) {
            throw new RuntimeException();
        }
    }
}
