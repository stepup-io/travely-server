package com.stepup.travely.security.jwt.Dao.entity;

import com.stepup.travely.common.entity.UuidEntity;
import com.stepup.travely.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_refresh_tokens", indexes = @Index(name = "refresh_token", columnList = "user_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends UuidEntity {

    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, updatable = false)
    private String refreshToken;

    public RefreshToken(User user, String refreshToken) {
        this.user = user;
        this.refreshToken = refreshToken;
    }
}
