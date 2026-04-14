package com.aitenant.auth.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
