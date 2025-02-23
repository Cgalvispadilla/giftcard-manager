package com.exito.giftcardmanager.infrastructure.adapter.jwt;

import com.exito.giftcardmanager.domain.model.user.TokenResponse;
import com.exito.giftcardmanager.domain.model.user.gateway.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProviderImpl implements JwtProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Override
    public TokenResponse generateToken(String userName) {
        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 3600_000);

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return TokenResponse.builder().token(token)
                .expiration(expiration)
                .createdAt(issuedAt)
                .type("Bearer")
                .build();
    }
    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Key getKey() {
        return key;
    }
}