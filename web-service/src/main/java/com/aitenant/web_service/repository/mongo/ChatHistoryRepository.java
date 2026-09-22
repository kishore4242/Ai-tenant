package com.aitenant.web_service.repository.mongo;

import com.aitenant.web_service.model.mongo.ChatHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatHistoryRepository extends MongoRepository<ChatHistory,String> {
    Optional<ChatHistory> findByTenantIdAndUserIdAndChatId(String tenantId, String userId, String chatId);
}
