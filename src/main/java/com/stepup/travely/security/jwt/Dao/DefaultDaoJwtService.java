package com.stepup.travely.security.jwt.Dao;

import com.stepup.travely.domain.user.entity.User;
import com.stepup.travely.security.interfaces.CustomUserDetails;
import com.stepup.travely.security.jwt.CustomJwtEncoder;
import com.stepup.travely.security.jwt.Dao.entity.RefreshToken;
import com.stepup.travely.security.jwt.JwtService;
import com.stepup.travely.security.jwt.RefreshTokenRepository;
import java.util.UUID;

public class DefaultDaoJwtService extends JwtService {

    private final RefreshTokenRepository repository;

    public DefaultDaoJwtService(CustomJwtEncoder encoder, RefreshTokenRepository repository) {
        super(encoder);
        this.repository = repository;
    }

    @Override
    public void removeRefreshToken(String sub) {
        UUID userId = UUID.fromString(sub);
        repository.deleteByUser_id(userId);
    }

    @Override
    protected void persistTokens(Tokens jwtTokens, CustomUserDetails userDetails) {
        new RefreshToken(new User(userDetails.getId()), jwtTokens.refreshToken());
    }
}
