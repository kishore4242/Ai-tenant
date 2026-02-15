package com.aitenant.auth.controller;

import com.aitenant.auth.service.PasswordChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordChangeController {
    private final PasswordChangeService passwordChangeService;

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam("email") String email){
        if(email != null){
            return passwordChangeService.sendCode(email);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("email") String email,@RequestParam("otp") String otp, @RequestParam("password") String password,
                                           @RequestParam("confirm-password") String confirmPassword){
        if(email != null  && password != null && confirmPassword != null && otp != null){
            return passwordChangeService.resetPassword(email, password, confirmPassword, otp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }
}
