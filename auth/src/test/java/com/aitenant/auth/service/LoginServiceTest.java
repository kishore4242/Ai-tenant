package com.aitenant.auth.service;


import com.aitenant.auth.filters.JWTFilterService;
import com.aitenant.auth.models.RegisterUser;
import com.aitenant.auth.repository.RegisterUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTFilterService jwtFilterService;

    @Mock
    private RegisterUserRepo registerUserRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    @Test
    void login_success_createUser(){
        when(registerUserRepo.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("password"))
                .thenReturn("hashed");
        ResponseEntity<?> response =
                loginService.register("test@example.com", "password","TCS");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(registerUserRepo).save(any(RegisterUser.class));
    }

    @Test
    void register_emailAlreadyExists_returns400() {
        when(registerUserRepo.findByEmail("test@example.com"))
                .thenReturn(Optional.of(new RegisterUser()));

        ResponseEntity<?> response =
                loginService.register("test@example.com", "password","TCS");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(" Email is already in registered", response.getBody());

        verify(registerUserRepo, never()).save(any());
    }

}
