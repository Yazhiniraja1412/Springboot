package com.cozyhaven.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;          // Base64-encoded key (≥ 32 bytes when decoded)

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;      // e.g. 86400000 = 24h

    /* ---------- helpers ---------- */

    /** Builds an HS-256 signing key from the configured Base-64 string. */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);

        // Fail-fast if someone configured a weak key (< 32 bytes)
        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                "jwt.secret must be at least 256 bits (32 bytes) after Base64 decoding. " +
                "Current length = " + (8 * keyBytes.length) + " bits.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /* ---------- public API ---------- */

    public String generateJwtToken(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                   .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid JWT token: " + e.getMessage());
            return false;
        }
    }

    /* ---------- private ---------- */

    private <T> T getClaimFromToken(String token, Function<Claims, T> extractor) {
        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(getSigningKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        return extractor.apply(claims);
    }
}
