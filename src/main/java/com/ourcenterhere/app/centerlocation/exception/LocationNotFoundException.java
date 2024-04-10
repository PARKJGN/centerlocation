package com.ourcenterhere.app.centerlocation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LocationNotFoundException extends RuntimeException{

    private final ErrorCode errorCode;
}
