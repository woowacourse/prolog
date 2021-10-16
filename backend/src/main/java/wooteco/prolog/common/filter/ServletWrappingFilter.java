package wooteco.prolog.common.filter;

import java.io.IOException;
import java.lang.reflect.Proxy;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.prolog.common.filter.proxy.ServletRequestCache;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(toProxy(request), response);
    }

    private HttpServletRequest toProxy(HttpServletRequest request) {
        return (HttpServletRequest) Proxy.newProxyInstance(
            getClass().getClassLoader(),
            new Class[]{HttpServletRequest.class},
            new ServletRequestCache(request)
        );
     }
}
