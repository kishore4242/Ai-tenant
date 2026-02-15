package com.aitenant.auth.controller;

import com.aitenant.auth.service.PasswordChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordChangeController {
    private final PasswordChangeService passwordChangeService;

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam("username") String email){
        if(email != null){
            return passwordChangeService.sendCode(email);
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }
}
