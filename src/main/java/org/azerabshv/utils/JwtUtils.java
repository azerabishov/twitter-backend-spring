package org.azerabshv.utils;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.azerabshv.security.UserDetailsImpl;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {


    public String generateToken(UserDetailsImpl userPrincipal) {
        return Jwts.builder()
                .claim("id", userPrincipal.getId())
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor("verylongsecertkeyverylongsecertkeyverylongsecertkey".getBytes()))
                .compact();

    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor("verylongsecertkeyverylongsecertkeyverylongsecertkey".getBytes()))
                .build().parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
