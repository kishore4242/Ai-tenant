package com.aitenant.auth.service;

import com.aitenant.auth.models.RegisterUser;
import com.aitenant.auth.repository.RegisterUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyUserDetails implements UserDetailsService {

    private final RegisterUserRepo registerUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterUser user = registerUserRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        log.info(user.getEmail(),user.getPassword());
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole())
                .disabled(false)
//                .accountLocked(!"INACTIVE".equals(user.getStatus()))
                .build();
    }
}
