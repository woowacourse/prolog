package wooteco.support.security.access;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.context.SecurityContextHolder;

@AllArgsConstructor
public class FilterSecurityInterceptor extends GenericFilterBean {

    private SecurityMetadataSource metadataSource;
    private AccessDecisionManager accessDecisionManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        FilterInvocation fi =
            new FilterInvocation((HttpServletRequest) request, (HttpServletResponse) response);

        Collection<ConfigAttribute> attributes = metadataSource.getAttributes(fi);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        accessDecisionManager.decide(authentication, fi, attributes);

        chain.doFilter(request, response);
    }
}
