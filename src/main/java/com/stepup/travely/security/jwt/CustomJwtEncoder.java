package com.stepup.travely.security.jwt;


import com.stepup.travely.security.interfaces.CustomUserDetails;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

@Component
public class CustomJwtEncoder {


    private final JwtEncoder accessJwtEncoder;
    private final JwtEncoder refreshJwtEncoder;
    @Value("${spring.auth.access-token.minute}")
    private Long ACCESS_TOKEN_EXPIRE_MINUTES;
    @Value("${spring.auth.refresh-token.day}")
    private Long REFRESH_TOKEN_EXPIRE_DAYS;

    public CustomJwtEncoder(@Qualifier("accessJwtEncoder") JwtEncoder accessJwtEncoder,
                            @Qualifier("refreshJwtEncoder") JwtEncoder refreshJwtEncoder) {
        this.accessJwtEncoder = accessJwtEncoder;
        this.refreshJwtEncoder = refreshJwtEncoder;
    }

    public String generateAccessToken(CustomUserDetails user) {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(ACCESS_TOKEN_EXPIRE_MINUTES, ChronoUnit.MINUTES);
        JwtClaimsSet claimsSet = JwtClaimsSet.builder().claim("sub", user.getId()).claim("roles",
            String.join(" ", user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toArray(String[]::new))).issuedAt(issuedAt).expiresAt(expiration).build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("jwt").build();
        return accessJwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet))
            .getTokenValue();
    }

    public String generateRefreshToken() {
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(REFRESH_TOKEN_EXPIRE_DAYS, ChronoUnit.DAYS);
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).type("jwt").build();
        JwtClaimsSet claimsSet = JwtClaimsSet.builder().issuedAt(issuedAt).expiresAt(expiration)
            .build();
        return refreshJwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet))
            .getTokenValue();
    }
}
