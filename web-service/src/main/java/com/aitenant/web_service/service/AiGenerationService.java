package com.aitenant.web_service.service;

import com.aitenant.web_service.dto.AiRequestDto;
import com.aitenant.web_service.model.mongo.ChatHistory;
import com.aitenant.web_service.model.History;
import com.aitenant.web_service.utils.SecurityUtils;
import com.aitenant.web_service.utils.StaticPrompts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class AiGenerationService {

    private final ChatClient chatClient;
    private final ChatHistoryService chatHistoryService;
    private final StaticPrompts staticPrompts;

    public AiGenerationService(ChatClient.Builder chatClientBuilder, ChatHistoryService chatHistoryService,StaticPrompts staticPrompts) {
        this.chatClient = chatClientBuilder.build();
        this.chatHistoryService = chatHistoryService;
        this.staticPrompts = staticPrompts;
    }
    @Value("classpath:/prompt/DemoChats.st")
    private Resource systemResource;


    /***
     This service is responsible for the generated ai response and provide output to the user
     Save the chat to database
     ***/
    public ResponseEntity<?> generate(AiRequestDto aiRequestDto,String chatId, HttpServletRequest request) throws AccessDeniedException {
        String userId = SecurityUtils.getCurrentUserId(request);
        String tenantId = SecurityUtils.getCurrentTenantId(request);

        // Check the session
        boolean isNewSession = (chatId == null || chatId.isBlank());
        ChatHistory session;
        if(isNewSession){
            session = buildNewSession(tenantId, userId);

        }else{
            session = chatHistoryService.getChat(tenantId,userId,chatId);
        }
        
        try {
            String response = chatClient
                    .prompt(aiRequestDto.getPrompt())
                    .call()
                    .content();
            log.info(response);
            List<History> historyList = session.getHistory();
            int nextSeq = historyList.isEmpty() ? 1 : historyList.getLast().getSeq() + 1;

            if(nextSeq == 1){
                CompletableFuture.runAsync(() -> {
                    String title = staticPrompts.generateChatTitle(aiRequestDto.getPrompt());
                    chatHistoryService.updateChatHistoryStatus(tenantId, userId, session.getChatId(), title);
                });
            }

            History userTurn = generateHistory(nextSeq, aiRequestDto.getPrompt(), "USER");
            History assistantTurn = generateHistory(nextSeq + 1, response, "ASSISTANT");
            session.getHistory().addAll(List.of(userTurn, assistantTurn));
            chatHistoryService.saveHistory(session);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error try again");
        }
    }

    private History generateHistory(int i, String response, String role) {
        History history = new History();
        history.setSeq(i);
        history.setContent(response);
        history.setTime(LocalDateTime.now());
        history.setRole(role);
        return history;
    }

    private static ChatHistory buildNewSession(String tenantId, String userId){
        return ChatHistory.builder()
                .tenantId(tenantId)
                .userId(userId)
                .chatId(UUID.randomUUID().toString())
                .title("New title")
                .history(new ArrayList<History>())
                .build();
    }

    public ResponseEntity<?> generateNewResponse(AiRequestDto aiRequestDto, HttpServletRequest request) {
        try {
            String response = chatClient
                    .prompt()
                    .system("""
                            You are a helpful AI assistant.

                            If the user asks for the current date or time,
                            use the getDate tool.

                            The getDate tool requires an IANA timezone ID.

                            Examples:
                            India -> Asia/Kolkata
                            Japan -> Asia/Tokyo
                            UK -> Europe/London
                            New York -> America/New_York
                            """)
                    .user(aiRequestDto.getPrompt())
                    .call()
                    .content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error {}", String.valueOf(e));
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getClass().getName() + " - " + e.getMessage()
                            + (e.getCause() != null ? " | Cause: " + e.getCause().getMessage() : ""));
        }
    }
}
