package com.stepup.travely.security.interfaces;

import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetails extends UserDetails {
    UUID getId();
}
