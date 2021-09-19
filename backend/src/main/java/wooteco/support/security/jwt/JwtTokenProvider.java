package wooteco.support.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenProvider {

    private String secretKey;
    private long expireLength;

    public String createToken(String username, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireLength);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(validity)
            .claim("role", role)
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

    public Map<String, Object> extractSubject(String token, List<String> keys) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().entrySet()
            .stream()
            .filter(it -> keys.contains(it.getKey()))
            .collect(Collectors.toMap(it -> it.getKey(), it -> it.getValue()));
    }
}
