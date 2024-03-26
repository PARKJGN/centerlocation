package com.ourcenterhere.app.centerlocation;

import com.ourcenterhere.app.centerlocation.SearchAddress.ApiSearchAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ApiSearchAddress apiSearchAddress;

    @GetMapping("/")
    public String home(){
        return "index";
    }

    @ResponseBody
    @GetMapping("/searchAddress")
    public String searchAddress(){
        String clientId = "HkjSzC6KOCeT5vWJCf_Z"; //애플리케이션 클라이언트 아이디
        String clientSecret = "VHK3kzwGUe"; //애플리케이션 클라이언트 시크릿

        String text = null;

        try {
            text = URLEncoder.encode("그린팩토리", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/local?query=" + text;    // JSON 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = apiSearchAddress.get(apiURL,requestHeaders);

        System.out.println(responseBody);
        return responseBody;
    }
}
