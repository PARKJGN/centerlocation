package com.ourcenterhere.app.centerlocation.CenterLocation.controller;

import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDtoList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CenterLocationController {

    @PostMapping("/search-center")
    public String SearchCenter(@ModelAttribute SearchLocationDtoList list){
        System.out.println(list);

        return "/";
    }
}
