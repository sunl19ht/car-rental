package com.sunl19ht.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtils {

    @Value("${car.jwt.admin-secret-key}")
    private String signature;

    @Value("${car.jwt.admin-ttl}")
    private long time;

    public String createJWT(String username) {
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("username", username)
                .claim("role", "admin")
                .setSubject("admin")
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }

    public Boolean checkToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
