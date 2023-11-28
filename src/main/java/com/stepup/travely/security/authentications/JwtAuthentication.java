package com.stepup.travely.security.authentications;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class JwtAuthentication extends AbstractAuthenticationToken {
    public JwtAuthentication(UserDetails member) {
        super(member.getAuthorities());
        setDetails(member);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
