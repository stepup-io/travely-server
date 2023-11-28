package com.stepup.travely.security.jwt;

import com.stepup.travely.security.interfaces.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JwtService {
    private final CustomJwtEncoder encoder;
    public Tokens createTokens(CustomUserDetails userDetails) {
        String accessToken = encoder.generateAccessToken(userDetails);
        String refreshToken = encoder.generateRefreshToken();
        Tokens jwtTokens = new Tokens(accessToken, refreshToken);
        persistTokens(jwtTokens, userDetails);
        return new Tokens(accessToken, refreshToken);
    }
    abstract public void removeRefreshToken(String sub);
    abstract protected void persistTokens(Tokens jwtTokens, CustomUserDetails userDetails);
    public record Tokens(String accessToken, String refreshToken) {}
}
