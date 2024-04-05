package com.ourcenterhere.app.centerlocation;

import com.ourcenterhere.app.centerlocation.searchLocation.ApiSearchLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/alone")
    public String aloneForm(Model model){
        return "/page/alone_form";
    }

    @GetMapping("/")
    public String home(Model model){
        return "index";
    }


}
