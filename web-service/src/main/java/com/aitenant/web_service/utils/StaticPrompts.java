package com.aitenant.web_service.utils;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StaticPrompts {

    private final ChatClient chatClient;

    public StaticPrompts(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String generateChatTitle(String prompt) {
        return chatClient
                .prompt("""
                    Generate a short title for this conversation.
                    Maximum 5 words.
                    Return only the title.

                    User message:
                    %s
                    """.formatted(prompt))
                .call()
                .content();
    }
}