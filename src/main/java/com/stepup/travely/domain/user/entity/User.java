package com.stepup.travely.domain.user.entity;

import com.stepup.travely.common.entity.UuidEntity;
import com.stepup.travely.security.interfaces.CustomUserDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends UuidEntity implements CustomUserDetails {
    private String email;

    public User(UUID id) {
        super(id);
    }

    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
