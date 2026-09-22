package com.aitenant.web_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDto {
    private String errorMessage;
    private LocalDateTime currentDate;

    public ErrorResponseDto(String errorMessage, LocalDateTime currentDate){
        this.errorMessage = errorMessage;
        this.currentDate = currentDate;
    }

}
