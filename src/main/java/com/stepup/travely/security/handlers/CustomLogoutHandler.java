package com.stepup.travely.security.handlers;


import com.stepup.travely.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final DefaultBearerTokenResolver defaultBearerTokenResolver = new DefaultBearerTokenResolver();
    private final JwtService jwtService;
    @Qualifier("accessJwtDecoder")
    private final JwtDecoder accessTokenDecoder;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        String accessToken = defaultBearerTokenResolver.resolve(request);
        Jwt decode = accessTokenDecoder.decode(accessToken);
        String sub = decode.getSubject();
        jwtService.removeRefreshToken(sub);
    }
}
