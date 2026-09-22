package com.aitenant.web_service.service;

import com.aitenant.web_service.model.jpa.ChatSession;
import com.aitenant.web_service.repository.jpa.ChatSessionRepository;
import com.aitenant.web_service.utils.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiResponseService {

    private final ChatSessionRepository chatSessionRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> fetchHistory(HttpServletRequest request) throws AccessDeniedException {
        String userId = SecurityUtils.getCurrentUserId(request);
        String tenantId = SecurityUtils.getCurrentTenantId(request);
        log.info("Fetching session details for user {} for tenant {}",userId,tenantId);
        List<ChatSession> dbResponse =chatSessionRepository.findByUserIdAndTenantId(userId,tenantId);
        dbResponse.forEach(cs -> log.info("chatId={}, title={}, tenantId={}, userId={}",
                cs.getChatId(), cs.getTitle(), cs.getTenantId(), cs.getUserId()));
        return ResponseEntity.ok().body(dbResponse);
    }
}
