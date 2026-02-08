package com.aitenant.auth.repository;

import com.aitenant.auth.models.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegisterUserRepo extends JpaRepository<RegisterUser,Long> {
    Boolean existsByEmail(String email);
    Optional<RegisterUser> findByEmail(String email);
}
