package wooteco.support.security.access;

import java.util.Collection;
import wooteco.support.security.access.matcher.RequestMatcher;

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
