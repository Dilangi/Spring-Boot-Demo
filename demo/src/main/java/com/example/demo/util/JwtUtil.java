package com.example.demo.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.demo.constants.AppConstants.JWT.*;

@Component
public class JwtUtil {

    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_VALIDITY);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_VALIDITY);
    }

    private String generateToken(String username, long validityMillis) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer("DemoApplication") // Optionally use your domain here
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(KEY)
                .compact();
    }

    public  String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parser()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
}
