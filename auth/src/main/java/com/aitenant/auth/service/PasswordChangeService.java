package com.aitenant.auth.service;

import com.aitenant.auth.models.RegisterUser;
import com.aitenant.auth.repository.RegisterUserRepo;
import com.aitenant.auth.utils.OtpGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordChangeService {
    private final RegisterUserRepo registerUserRepo;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> sendCode(String email) {
        if(registerUserRepo.existsByEmail(email)){
            String otp = OtpGenerator.generateOtp();
            String key = "RESET_OTP:"+email;
            redisTemplate.opsForValue().set(key,otp, Duration.ofMinutes(10));
            emailService.sendOtpEmail(email, otp);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Otp send successfully.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occur Try Again.");
    }

    @Transactional
    public ResponseEntity<?> resetPassword(String email, String password, String confirmPassword, String otp) {
        if (!password.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Password mismatch");
        }

        String key = "RESET_OTP:" + email;
        String savedOtp = redisTemplate.opsForValue().get(key);
        if (savedOtp == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("OTP expired or not found");
        }

        if (!savedOtp.equals(otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid OTP");
        }
        String passwordEncoded = passwordEncoder.encode(confirmPassword);
        RegisterUser user = registerUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPassword(passwordEncoded);
        redisTemplate.delete(key);
        return ResponseEntity.ok("Password updated");
    }
}
