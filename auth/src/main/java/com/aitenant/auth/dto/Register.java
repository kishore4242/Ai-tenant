package com.aitenant.auth.dto;

import lombok.Data;

@Data
public class Register {
    private String username;
    private String password;
    private String tenantName;
}
