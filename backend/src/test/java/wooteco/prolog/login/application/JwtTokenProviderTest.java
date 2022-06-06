package wooteco.prolog.login.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import wooteco.prolog.login.excetpion.TokenNotValidException;
import wooteco.prolog.member.domain.Role;

@ActiveProfiles("test")
@SpringBootTest
class JwtTokenProviderTest {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @DisplayName("유효하지 않은 JWT 토큰이 입력됐을 때 TokenNotValidException이 발생하는지 확인 ")
    @Test
    void jwtTokenProviderValidationCheck() {
        //given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 100);

        String expiredToken = Jwts.builder()
            .setSubject("1")
            .setIssuedAt(now)
            .setExpiration(expiredDate)
            .claim("role", Role.CREW)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();

        String malformedToken = "단단히잘못된토큰";
        //when
        //then
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(expiredToken))
            .isInstanceOf(TokenNotValidException.class);
        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(malformedToken))
            .isInstanceOf(TokenNotValidException.class);
    }
}
