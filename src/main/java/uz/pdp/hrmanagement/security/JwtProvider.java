package uz.pdp.hrmanagement.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private static final String KEY = "SmoothLikeButter_By_BTS";
    private static final Long EXPIRE_DATE = 1000 * 60 * 60 * 24L;

    public String generateToken(String username) {

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DATE))
//                .claim("roles", user.getRole().getAuthority())
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public String getEmailFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts
                    .parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
