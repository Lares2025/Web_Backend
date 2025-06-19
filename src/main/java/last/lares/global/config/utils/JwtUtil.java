package last.lares.global.config.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    @Value("${jwt.expiration}")
    private Long exprTime;

    public String createJwt(String userId, String userRole) {
         Claims claims = Jwts.claims();
         claims.put("userId", userId);
         claims.put("userRole", userRole);
         Date now = new Date();

         return Jwts.builder()
                 .setClaims(claims)
                 .setIssuedAt(now)
                 .setExpiration(new Date(now.getTime() + exprTime))
                 .signWith(key, SignatureAlgorithm.HS256)
                 .compact();
    }

    public String getUserId(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userId", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public String getUserRole(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .get("userRole", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
