package com.aitenant.web_service.repository.jpa;

import com.aitenant.web_service.model.jpa.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    @Query(value = """
    SELECT *
    FROM chat_history
    WHERE user_id = :userId
      AND tenant_id = :tenantId
    ORDER BY created_at
    """, nativeQuery = true)
    List<ChatSession> findByUserIdAndTenantId(
            @Param("userId") String userId,
            @Param("tenantId") String tenantId
    );
}
