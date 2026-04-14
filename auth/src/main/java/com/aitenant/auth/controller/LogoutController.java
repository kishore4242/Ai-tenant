package com.aitenant.auth.controller;


import com.aitenant.auth.dto.RefreshTokenRequest;
import com.aitenant.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<?> logoutController(@RequestHeader("Authorization") String accessToken,@RequestBody RefreshTokenRequest token){
        return logoutService.logoutUser(accessToken,token);
    }
}
