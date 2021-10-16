package wooteco.prolog.common.filter;

import java.io.IOException;
import java.lang.reflect.Proxy;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import wooteco.prolog.common.filter.proxy.ServletRequestCache;

@WebFilter(urlPatterns = "/**")
public class ServletWrappingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        chain.doFilter(toProxy(request), response);
    }

    private ServletRequest toProxy(ServletRequest request) {
        return (ServletRequest) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{ServletRequest.class},
            new ServletRequestCache(request)
        );
     }

    @Override
    public void destroy() {
    }
}
