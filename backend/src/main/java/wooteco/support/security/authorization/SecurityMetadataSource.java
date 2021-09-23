package wooteco.support.security.authorization;

import java.util.Collection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import wooteco.support.security.authorization.requestmatcher.RequestMatcher;

@AllArgsConstructor
public class SecurityMetadataSource {

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    public Collection<ConfigAttribute> getAttributes(FilterInvocation fi) {
        final HttpServletRequest request = fi.getRequest();
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
