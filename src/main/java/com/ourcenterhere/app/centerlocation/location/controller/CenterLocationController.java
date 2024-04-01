package com.ourcenterhere.app.centerlocation.location.controller;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDtoList;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CenterLocationController {

    private final LocationService centerLocationService;
    private final RoomService roomService;

    @PostMapping("/search-center")
    public String SearchCenter(@ModelAttribute SearchLocationDtoList list, Model model){

        List<SearchLocationDto> searchLocationDtoList = list.getSearchLocationDtoList();
        Map<String, Double> center = centerLocationService.centerLocation(searchLocationDtoList);

        roomService.enrollRoom(searchLocationDtoList);

        model.addAttribute("locationList", searchLocationDtoList);
        model.addAttribute("center", center);

        return "/page/center_location";
    }
}
