package wooteco.support.security.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import lombok.AllArgsConstructor;
import wooteco.prolog.member.domain.Member;

@AllArgsConstructor
public class JwtTokenProvider {

    private String secretKey;
    private long expireLength;

    public String createToken(Member member) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
            .setSubject(member.getId().toString())
            .setIssuedAt(now)
            .setExpiration(validity)
            .claim("role", member.getRole())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
}
