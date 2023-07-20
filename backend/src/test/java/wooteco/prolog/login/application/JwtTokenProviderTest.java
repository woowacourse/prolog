package wooteco.prolog.login.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static wooteco.prolog.common.exception.BadRequestCode.TOKEN_NOT_VALID;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.prolog.common.exception.BadRequestException;
import wooteco.prolog.member.domain.Role;

class JwtTokenProviderTest {

    @DisplayName("유효하지 않은 JWT 토큰이 입력됐을 때 TokenNotValidException이 발생하는지 확인 ")
    @Test
    void jwtTokenProviderValidationCheck() {
        //given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 100);
        String secretKey = "secret_key";

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
            .isInstanceOf(BadRequestException.class)
            .hasMessage(TOKEN_NOT_VALID.getMessage());

        assertThatThrownBy(() -> jwtTokenProvider.extractSubject(malformedToken))
            .isInstanceOf(BadRequestException.class)
            .hasMessage(TOKEN_NOT_VALID.getMessage());
    }
}
