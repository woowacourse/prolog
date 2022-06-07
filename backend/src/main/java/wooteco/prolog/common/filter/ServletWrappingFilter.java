package wooteco.prolog.common.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import wooteco.prolog.common.slacklogger.RequestStorage;

@Component
public class ServletWrappingFilter extends OncePerRequestFilter {

    private final RequestStorage requestStorage;

    public ServletWrappingFilter(RequestStorage requestStorage) {
        this.requestStorage = requestStorage;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        requestStorage.set(wrappedRequest);

        filterChain.doFilter(wrappedRequest, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return "/rss".equals(request.getRequestURI());
    }
}
