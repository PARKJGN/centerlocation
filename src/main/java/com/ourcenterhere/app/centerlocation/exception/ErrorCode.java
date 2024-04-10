package com.ourcenterhere.app.centerlocation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_MATCH_ROOM_TYPE(HttpStatus.BAD_REQUEST, "해당 방 코드는 유효하지 않습니다."),

    NOT_FOUND_ROOM(HttpStatus.NOT_FOUND, "없는 방 코드 입니다."),

    NOT_FOUND_LOCATION(HttpStatus.NOT_FOUND, "이미 삭제된 장소입니다."),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "유효한 값이 아닙니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "오류가 발생했습니다. 다시 시도해주세요.");




    

    private final HttpStatus httpStatus;

    private final String message;

}
