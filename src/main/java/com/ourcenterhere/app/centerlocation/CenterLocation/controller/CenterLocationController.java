package com.ourcenterhere.app.centerlocation.CenterLocation.controller;

import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.CenterLocation.dto.SearchLocationDtoList;
import com.ourcenterhere.app.centerlocation.CenterLocation.service.CenterLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CenterLocationController {

    private final CenterLocationService centerLocationService;

    @PostMapping("/search-center")
    public String SearchCenter(@ModelAttribute SearchLocationDtoList list){

        List<SearchLocationDto> searchLocationDtoList = list.getSearchLocationDtoList();
        centerLocationService.centerLocation(searchLocationDtoList);

        return "/page/center_location";
    }
}
