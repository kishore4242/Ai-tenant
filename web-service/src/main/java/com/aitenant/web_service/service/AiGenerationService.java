// java
package com.aitenant.web_service.service;

import com.aitenant.web_service.dto.AiRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiGenerationService {

    private final ChatClient chatClient;

    public AiGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    @Value("classpath:/prompt/DemoChats.st")
    private Resource systemResource;

    public ResponseEntity<?> generate(AiRequestDto aiRequestDto, String tenantId, String email) {
        try {
            String response = chatClient
                    .prompt(aiRequestDto.getPrompt())
                    .system(systemResource)
                    .call()
                    .content();
            System.out.println(response);
            log.info(response);


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error try again");
        }
    }
}
