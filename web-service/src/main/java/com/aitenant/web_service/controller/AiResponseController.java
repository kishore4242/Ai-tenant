package com.aitenant.web_service.controller;

import com.aitenant.web_service.service.AiResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai")
@Tag(name="Data handler", description = "Controller responsible for fetching data from database")
public class AiResponseController {

    private final AiResponseService aiResponseService;

    @GetMapping("/history")
    @Operation(summary = "Get the history of chat", description = "Return the history of the user chat")
    public ResponseEntity<?> getHistoryChat(HttpServletRequest request) throws AccessDeniedException {
        return aiResponseService.fetchHistory(request);
    }
}
