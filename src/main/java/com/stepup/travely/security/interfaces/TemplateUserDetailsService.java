package com.stepup.travely.security.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TemplateUserDetailsService extends UserDetailsService {
    UserDetails getUserById(Long id);
    void updateRefreshTokenById(String refreshToken, Long id);
}
