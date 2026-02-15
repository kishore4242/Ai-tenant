package com.aitenant.auth.service;

import com.aitenant.auth.repository.RegisterUserRepo;
import com.aitenant.auth.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PasswordChangeService {
    private final RegisterUserRepo registerUserRepo;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;

    public ResponseEntity<?> sendCode(String email) {
        if(registerUserRepo.existsByEmail(email)){
            String otp = OtpGenerator.generateOtp();
            redisTemplate.opsForValue().set(email,otp, Duration.ofMinutes(10));
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Otp send successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occur Try Again.");
    }
}
