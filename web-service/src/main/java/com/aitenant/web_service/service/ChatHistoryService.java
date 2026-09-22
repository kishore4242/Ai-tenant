package com.aitenant.web_service.service;

import com.aitenant.web_service.exception.ChatSessionNotFoundException;
import com.aitenant.web_service.model.mongo.ChatHistory;
import com.aitenant.web_service.model.jpa.ChatSession;
import com.aitenant.web_service.repository.mongo.ChatHistoryRepository;
import com.aitenant.web_service.repository.jpa.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatHistoryService {
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatSessionRepository chatSessionRepository;

    public ChatHistory getChat(String tenantId, String userId,String chatId){
        return chatHistoryRepository.findByTenantIdAndUserIdAndChatId(tenantId,userId,chatId)
                .orElseThrow(() -> new ChatSessionNotFoundException(
                        "Session not found for chatId "+ chatId
                ));
    }

    public void saveHistory(ChatHistory history){
        chatHistoryRepository.save(history);
    }

    @Transactional(readOnly = false)
    public void updateChatHistoryStatus(String tenantId,String userId,String chatId,String title){
        ChatSession session = ChatSession.builder()
                .tenantId(tenantId)
                .userId(userId)
                .chatId(chatId)
                .title(title)
                .build();
        chatSessionRepository.save(session);
    }
}
