package wooteco.support.security.authorization;

import java.util.Collection;
import wooteco.support.security.authorization.requestmatcher.RequestMatcher;

public class UrlMapping {

    private RequestMatcher requestMatcher;
    private Collection<ConfigAttribute> configAttrs;

    public UrlMapping(RequestMatcher requestMatcher, Collection<ConfigAttribute> configAttrs) {
        this.requestMatcher = requestMatcher;
        this.configAttrs = configAttrs;
    }

    public RequestMatcher getRequestMatcher() {
        return requestMatcher;
    }

    public Collection<ConfigAttribute> getConfigAttrs() {
        return configAttrs;
    }
}
