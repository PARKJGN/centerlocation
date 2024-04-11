package com.ourcenterhere.app.centerlocation.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {
    private HttpStatus statusCode;
    private String resultMsg;
    private String resultData;

    public ResponseDto(final HttpStatus statusCode, final String resultMsg) {
        this.statusCode = statusCode;
        this.resultMsg = resultMsg;
        this.resultData = null;
    }

    public static ResponseDto res(final HttpStatus statusCode, final String resultMsg) {
        return res(statusCode, resultMsg, null);
    }

    public static ResponseDto res(final HttpStatus statusCode, final String resultMsg, final String t) {
        return ResponseDto.builder()
                .statusCode(statusCode)
                .resultMsg(resultMsg)
                .resultData(t)
                .build();
    }
}
