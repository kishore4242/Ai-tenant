package com.aitenant.web_service.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class SecurityUtils {
    public static String getCurrentUserId(HttpServletRequest request) throws AccessDeniedException {
        String userId = request.getHeader("X-User-Id");
        if(userId == null || userId.isBlank()){
            throw new AccessDeniedException("Missing user id");
        }
        return userId;
    }

    public static String getCurrentTenantId(HttpServletRequest request) throws AccessDeniedException {
        String tenantId = request.getHeader("X-Tenant-Id");
        if(tenantId == null || tenantId.isBlank()){
            throw new AccessDeniedException("Missing tenant id");
        }
        return tenantId;
    }
}
