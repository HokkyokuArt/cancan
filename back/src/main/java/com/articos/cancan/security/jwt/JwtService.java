package com.articos.cancan.security.jwt;

import com.nimbusds.jose.jwk.source.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.*;

import javax.crypto.spec.*;
import java.nio.charset.*;
import java.time.*;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final long expirationSeconds;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration}") long expirationSeconds
    ) {
        var key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.jwtEncoder = new NimbusJwtEncoder(new ImmutableSecret<>(key));
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(key).build();
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String email) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("cancan")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationSeconds))
                .subject(email)
                .build();

        JwsHeader header = JwsHeader.with(() -> "HS256").build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }

    public String extractSubject(String token) {
        return jwtDecoder.decode(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}