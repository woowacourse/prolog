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
import wooteco.support.security.exception.AuthenticationException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = AuthorizationExtractor.extract(request);
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(accessToken);

            Authentication authentication = jwtAuthenticationProvider
                .authenticate(jwtAuthenticationToken);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}