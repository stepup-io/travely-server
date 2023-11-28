package com.stepup.travely.security.oauth2.entity;


import com.stepup.travely.common.entity.UuidEntity;
import jakarta.persistence.Entity;

@Entity
public class OAuth2User extends UuidEntity {
    private String provider;
    private String providerId;
    private String profileImageUrl;
    private String name;
    private String accessToken;
    private String refreshToken;
}
