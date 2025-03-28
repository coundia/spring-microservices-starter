package com.pcoundia.gateway_server.exception;

import com.pcoundia.gateway_server.dto.ApiResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleGlobalException(Exception exception) {
        log.error("*****************************");
        log.error(exception.getMessage(), exception);
        log.error("*****************************");
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                exception.getMessage()
        );

        ApiResponseDto responseDto = ApiResponseDto
                .builder()
                .message("Erreur ")
                .code(0)
                .data(errorResponse)
                .build();
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@Data
@AllArgsConstructor
class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
