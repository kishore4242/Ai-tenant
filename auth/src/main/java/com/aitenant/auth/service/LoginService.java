package com.aitenant.auth.service;

import com.aitenant.auth.configuration.TenantCacheLoader;
import com.aitenant.auth.filters.JWTFilter;
import com.aitenant.auth.models.RegisterUser;
import com.aitenant.auth.models.Tenant;
import com.aitenant.auth.repository.RegisterUserRepo;
import com.aitenant.auth.repository.TenantRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JWTFilter jwtFilter;
    private final RegisterUserRepo registerUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate redisTemplate;
    private final TenantRepo tenantRepo;

    public ResponseEntity<?> login(String username, String password) {
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            RegisterUser user = registerUserRepo
                    .findByEmail(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String jwtToken = jwtFilter.generateToken(
                    userDetails.getUsername(),
                    user.getTenant().getId()

            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .body("Authentication successful");
        }
        catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Transactional
    public ResponseEntity<?> register(String username, String password, String tenantName) {
        try {
            if (registerUserRepo.findByEmail(username).isPresent()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(" Email is already in registered");
            }
            Object tenantIdObj = redisTemplate.opsForHash().get("TENANT:" + tenantName, "id");

            if (tenantIdObj == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Tenant not found");
            }
            Long tenant = Long.parseLong(tenantIdObj.toString());
            Tenant tenantObj = new Tenant();
            tenantObj.setId(tenant);
            RegisterUser users = new RegisterUser();
            users.setEmail(username);
            users.setPassword(passwordEncoder.encode(password));
            users.setTenant(tenantObj);

            users.setRole("ROLE_USER");
            users.setStatus("INACTIVE");

            registerUserRepo.save(users);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Account registered");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong"+e.getMessage());
        }
    }
}
