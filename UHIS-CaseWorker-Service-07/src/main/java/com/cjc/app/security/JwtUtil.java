package com.cjc.app.security;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private String secret = "UHIS_CASEWORKER_SECRET_KEY";

    private long expirationTime = 1000 * 60 * 60; 

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getEmailFromToken(String token) {

        token = removeBearerPrefix(token);

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {

        try {
            token = removeBearerPrefix(token);

            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    private String removeBearerPrefix(String token) {

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return token;
    }
}
