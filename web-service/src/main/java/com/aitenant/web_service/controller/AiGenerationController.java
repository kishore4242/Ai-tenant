package com.aitenant.web_service.controller;

import com.aitenant.web_service.dto.AiRequestDto;
import com.aitenant.web_service.service.AiGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AIResponseGeneratorController", description = "Generating the AI response from the LLM based on input")
public class AiGenerationController {

    private final AiGenerationService aiGenerationService;

//    @PostMapping("/generate")
    public ResponseEntity<?> generateResponse(@RequestBody @Valid AiRequestDto aiRequestDto,
                                              @RequestHeader("X-tenant-Id") String tenantId,
                                              @RequestHeader("X-username") String email){
//        return aiGenerationService.generate(aiRequestDto, tenantId, email);
        return null;
    }

    @PostMapping("/generate")
    @Operation(summary = "Generating the Response", description = "generating the response based on the user input prompt")
    public ResponseEntity<?> generateResponse(@RequestBody @Valid AiRequestDto aiRequestDto, @RequestParam String chatId, HttpServletRequest request) throws AccessDeniedException {
        return aiGenerationService.generate(aiRequestDto, chatId, request);
    }

    @PostMapping("/new/generate")
    @Operation(summary = "Generating the Response", description = "generating the new chat based on the user input prompt")
    public ResponseEntity<?> generateNewResponse(@RequestBody @Valid AiRequestDto aiRequestDto, HttpServletRequest request) throws AccessDeniedException {
        return aiGenerationService.generateNewResponse(aiRequestDto, request);
    }


    @PostMapping("/rag/generate")
    @Operation(summary = "Generate the response from RAG", description = "Generate the rag based response ")
    public ResponseEntity<?> generateResponse(@RequestBody @Valid AiRequestDto aiRequestDto){
        return null;
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck(){
        return ResponseEntity.ok().body("Healthy status");
    }
}
