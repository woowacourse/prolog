package wooteco.prolog.login.ui;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.prolog.login.application.JwtTokenProvider;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.support.security.authentication.AuthorizationExtractor;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private static final String ORIGIN = "Origin";
    private static final String ACCESS_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String ACCESS_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isPreflighted(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (HttpMethod.GET.matches(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!jwtTokenProvider.validateToken(AuthorizationExtractor.extract(request))) {
            throw new TokenNotValidException();
        }

        filterChain.doFilter(request, response);
    }

    private boolean isPreflighted(HttpServletRequest request) {
        return isOptionsMethod(request)
            && hasOrigin(request)
            && hasRequestHeaders(request)
            && hasRequestMethods(request);
    }

    public boolean isOptionsMethod(HttpServletRequest request) {
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    public boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ORIGIN));
    }

    public boolean hasRequestMethods(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_METHOD));
    }

    public boolean hasRequestHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader(ACCESS_REQUEST_HEADERS));
    }
}