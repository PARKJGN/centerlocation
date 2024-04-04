package com.ourcenterhere.app.centerlocation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class ErrorDetails {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int code;
    private final String error;
    private final String message;

    @Builder
    public ErrorDetails(ErrorCode errorCode) {
        this.code = errorCode.getHttpStatus().value();
        this.error = errorCode.getHttpStatus().name();
        this.message = errorCode.getMessage();
    }
}


