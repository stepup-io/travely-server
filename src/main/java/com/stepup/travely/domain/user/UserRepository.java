package com.stepup.travely.domain.user;

import com.stepup.travely.domain.user.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

}
