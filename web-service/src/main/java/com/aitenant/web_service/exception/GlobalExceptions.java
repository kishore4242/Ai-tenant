package com.aitenant.web_service.exception;

import com.aitenant.web_service.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(ResponseNotProvidedException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<ErrorResponseDto> responseNotProvidedHandler(ResponseNotProvidedException exception, WebRequest webRequest){
        ErrorResponseDto dto = new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(dto);
    }

    @ExceptionHandler(ChatSessionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> chatNotFound(ChatSessionNotFoundException exception, WebRequest webRequest){
        ErrorResponseDto dto = new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> accessDeniedException(AccessDeniedException exception, WebRequest webRequest){
        ErrorResponseDto dto = new ErrorResponseDto(
                exception.getMessage(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
}
