package com.calculadora_carbono.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtEncoder encoder;

    public String generateToken(String email){

        Instant now = Instant.now();
        long expire = 3600L;

        var claims = JwtClaimsSet.builder()
                .issuer("calculadora-carbono")
                .subject(email)
                .expiresAt(now.plusSeconds(expire))
                .issuedAt(now)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
