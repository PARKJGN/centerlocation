package com.ourcenterhere.app.centerlocation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoomNotMatchTypeException extends RuntimeException {
    public RoomNotMatchTypeException(String message) {
        super(message);
    }
}
