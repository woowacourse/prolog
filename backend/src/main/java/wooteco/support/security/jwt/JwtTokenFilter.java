package wooteco.support.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.authentication.AuthorizationExtractor;
import wooteco.support.security.context.SecurityContext;
import wooteco.support.security.context.SecurityContextHolder;
import wooteco.support.security.userdetails.UserDetails;
import wooteco.support.security.userdetails.UserDetailsService;

@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
//        if (HttpMethod.GET.matches(request.getMethod())) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (!jwtTokenProvider.validateToken(AuthorizationExtractor.extract(request))) {
            filterChain.doFilter(request, response);
            return;
        }
        String credentials = AuthorizationExtractor.extract(request);
        String username = jwtTokenProvider.extractSubject(credentials);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        Authentication authentication = new AuthenticationToken(userDetails);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(request, response);
    }
}