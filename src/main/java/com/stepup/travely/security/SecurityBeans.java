package com.stepup.travely.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.stepup.travely.security.jwt.CustomJwtEncoder;
import com.stepup.travely.security.jwt.Dao.DefaultDaoJwtService;
import com.stepup.travely.security.jwt.JwtService;
import com.stepup.travely.security.jwt.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
public class SecurityBeans {

    @Value("${spring.auth.access-token.secret}")
    private String ACCESS_SECRET_KEY;
    @Value("${spring.auth.refresh-token.secret}")
    private String REFRESH_SECRET_KEY;

    @Bean(name = "accessJwtDecoder")
    @Primary
    public JwtDecoder accessJwtDecoder() {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(generateKey(ACCESS_SECRET_KEY));
    }

    @Bean(name = "refreshJwtDecoder")
    public JwtDecoder refreshJwtDecoder() {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(generateKey(REFRESH_SECRET_KEY));
    }

    @Bean(name = "accessJwtEncoder")
    @Primary
    public JwtEncoder accessJwtEncoder() {
        return new NimbusJwtEncoder(generateKey(ACCESS_SECRET_KEY));
    }

    @Bean(name = "refreshJwtEncoder")
    public JwtEncoder refreshJwtEncoder() {
        return new NimbusJwtEncoder(generateKey(REFRESH_SECRET_KEY));
    }

    private JWKSource<SecurityContext> generateKey(String secret) {
        return new ImmutableSecret<>(secret.getBytes());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationConverter jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");
        converter.setAuthoritiesClaimName("roles");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public JwtService jwtService(CustomJwtEncoder customJwtEncoder,
                                 RefreshTokenRepository repository) {
        return new DefaultDaoJwtService(customJwtEncoder, repository);
    }
}
