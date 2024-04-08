package com.ourcenterhere.app.centerlocation.kakaoSearchLocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class KakaoSearchLocationController {

    private final ApiSearchLocation apiSearchLocation;

    @ResponseBody
    @GetMapping("/search-location/{text}")
    public String searchLocationFromKakao(@PathVariable String text){
        String Authorization = "KakaoAK 07cafd6c73572150c5d5f6dda1bbdd43"; //애플리케이션 클라이언트 아이디

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://dapi.kakao.com/v2/local/search/keyword?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

        String responseBody = apiSearchLocation.get(apiURL,Authorization);

        System.out.println(responseBody);
        return responseBody;
    }
}

