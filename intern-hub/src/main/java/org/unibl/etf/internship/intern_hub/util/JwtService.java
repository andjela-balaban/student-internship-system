package org.unibl.etf.internship.intern_hub.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtService {
	
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	
	@Value("${application.security.jwt.expiration}")
	private long jwtExpiration;
	
	public String generateToken(Integer userId, String username, String role) {
        Map<String, Object> claims = Map.of("userId", userId, "role", role);
        
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }
	
	public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
	
	public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
	
	public boolean isTokenValid(String token) {
        return !extractAllClaims(token).getExpiration().before(new Date());
    }
		
	private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

	private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}