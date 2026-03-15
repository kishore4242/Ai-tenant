package com.aitenant.web_service.controller;

import com.aitenant.web_service.dto.AiRequestDto;
import com.aitenant.web_service.service.AiGenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiGenerationController {

    private final AiGenerationService aiGenerationService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateResponse(@RequestBody @Valid AiRequestDto aiRequestDto,
                                              @RequestHeader("X-tenant-Id") String tenantId,
                                              @RequestHeader("X-user-email") String email){
        return aiGenerationService.generate(aiRequestDto, tenantId, email);
    }
}
