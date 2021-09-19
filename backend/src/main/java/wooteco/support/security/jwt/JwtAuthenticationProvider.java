package wooteco.support.security.jwt;

import java.util.Arrays;
import java.util.Map;
import lombok.AllArgsConstructor;
import wooteco.support.security.authentication.Authentication;
import wooteco.support.security.authentication.AuthenticationProvider;
import wooteco.support.security.authentication.AuthenticationToken;
import wooteco.support.security.exception.AuthenticationException;

@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Authentication authenticate(AuthenticationToken authenticationToken) {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authenticationToken;
        String accessToken = jwtAuthenticationToken.getAccessToken();

        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new AuthenticationException("로그인 토큰이 유효하지 않습니다.");
        }

        Map<String, Object> claimMap = jwtTokenProvider
            .extractSubject(accessToken, Arrays.asList("sub", "role"));

        return new JwtAuthentication(claimMap.get("sub") + "");
    }

    @Override
    public boolean supports(Class<?> authenticationToken) {
        return JwtAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }
}
