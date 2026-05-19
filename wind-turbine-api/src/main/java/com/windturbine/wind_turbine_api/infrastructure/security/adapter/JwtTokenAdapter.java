package com.windturbine.wind_turbine_api.infrastructure.security.adapter;

import com.windturbine.wind_turbine_api.application.port.TokenServicePort;
import com.windturbine.wind_turbine_api.domain.model.User;
import com.windturbine.wind_turbine_api.infrastructure.security.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * JWT implementation of {@link TokenServicePort}.
 * Builds HMAC-SHA256 signed tokens carrying user identity and role.
 */
@Component
public class JwtTokenAdapter implements TokenServicePort {

    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_USERNAME = "username";
    public static final String CLAIM_EMAIL = "email";

    private final JwtProperties properties;
    private final SecretKey signingKey;
    private final Duration tokenLifetime;

    public JwtTokenAdapter(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(
                properties.secretKey().getBytes(StandardCharsets.UTF_8)
        );
        this.tokenLifetime = Duration.ofMinutes(properties.expirationMinutes());
    }

    @Override
    public GeneratedToken generateToken(User user, String roleName) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(tokenLifetime);

        String token = Jwts.builder()
                .issuer(properties.issuer())
                .audience().add(properties.audience()).and()
                .subject(String.valueOf(user.getUserId()))
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .claims(Map.of(
                        CLAIM_USERNAME, user.getUsername(),
                        CLAIM_EMAIL, user.getEmail(),
                        CLAIM_ROLE, roleName
                ))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();

        return new GeneratedToken(token, expiresAt);
    }

    /**
     * Parses and verifies the token. Throws {@link JwtException} subclasses
     * for any failure (bad signature, expired, malformed, wrong issuer, ...).
     * The auth filter catches these and lets Spring Security return 401.
     */
    public Jws<Claims> parse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(properties.issuer())
                .requireAudience(properties.audience())
                .build()
                .parseSignedClaims(token);
    }
}
