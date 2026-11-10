package com.teachhub.backend.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY_STRING="enoeniednijeni3jeniknqwlkqnwl3jen";
    private final SecretKey SECRET_KEY= Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    public String generateToken(@Nullable UserDetails userDetails){
        if (userDetails == null) {
            return "";
        }
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .issuedAt(new Date())
                .signWith(SECRET_KEY,Jwts.SIG.HS256)
                .compact();
    }
    public String extractUsername(String token){
        return Jwts
                .parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
//                .getSubject().equals(userDetails.getUsername());
    }
}
