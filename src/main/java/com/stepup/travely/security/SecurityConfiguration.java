package com.stepup.travely.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(jsr250Enabled = true) //@RolesAllowed({BUYER}) 처럼 컨트롤러에서 사용하기 위함입니다.
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationConverter converter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint restAuthenticationEntryPoint;
    private final CorsConfigurationSource corsConfigurationSource;
    private final LogoutHandler logoutHandler;
    private final AuthenticationSuccessHandler loginSuccessHandler;
    private final AuthenticationFailureHandler loginFailureHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    @Qualifier("accessJwtDecoder")
    private JwtDecoder jwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).cors(
                httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                    corsConfigurationSource)).sessionManagement(
                (session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
            .authorizeHttpRequests(registry -> registry.anyRequest().permitAll()).logout(
                (logout) -> logout.logoutUrl("/api/v1/auth/logout").addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler(logoutSuccessHandler)).oauth2ResourceServer(
                oauth2 -> oauth2.jwt(
                        jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(converter)
                            .decoder(jwtDecoder)).accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(restAuthenticationEntryPoint)).formLogin(
                config -> config.loginProcessingUrl("/api/v1/auth/login")
                    .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler));
        return http.build();
    }

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder)
        throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public ProviderManager authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(daoAuthenticationProvider);
    }
}

