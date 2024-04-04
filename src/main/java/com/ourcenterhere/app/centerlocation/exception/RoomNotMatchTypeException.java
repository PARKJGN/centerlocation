package com.ourcenterhere.app.centerlocation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@RequiredArgsConstructor
public class RoomNotMatchTypeException extends RuntimeException {
    private final ErrorCode errorCode;
}
