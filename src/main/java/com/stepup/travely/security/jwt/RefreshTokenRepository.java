package com.stepup.travely.security.jwt;

import com.stepup.travely.security.jwt.Dao.entity.RefreshToken;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    void deleteByUser_id(UUID user_id);
}