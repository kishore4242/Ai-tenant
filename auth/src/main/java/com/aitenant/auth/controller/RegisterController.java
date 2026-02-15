package com.aitenant.auth.controller;


import com.aitenant.auth.dto.Register;
import com.aitenant.auth.dto.Login;
import com.aitenant.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class RegisterController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody Login login){
        return loginService.login(login.getUsername(), login.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Register login){
        return loginService.register(login.getUsername(), login.getPassword(), login.getTenantName());
    }
}