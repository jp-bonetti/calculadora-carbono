package com.calculadora_carbono.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public String generateToken(String email, Long userId){

        Instant now = Instant.now();
        long expire = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("calculadora-carbono")
                .subject(email)
                .expiresAt(now.plusSeconds(expire))
                .issuedAt(now)
                .claim("userId", userId)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long extractUserId(String token) {
        Jwt jwt = decoder.decode(token);
        return jwt.getClaim("userId");
    }

}
