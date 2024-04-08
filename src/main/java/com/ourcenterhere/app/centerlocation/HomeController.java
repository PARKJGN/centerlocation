package com.ourcenterhere.app.centerlocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
