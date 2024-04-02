package com.ourcenterhere.app.centerlocation.location.controller;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDtoList;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CenterLocationController {

    private final LocationService centerLocationService;
    private final RoomService roomService;

    @PostMapping("/search-center")
    public String SearchCenter(@ModelAttribute SearchLocationDtoList list, Model model){

        List<SearchLocationDto> searchLocationDtoList = list.getSearchLocationDtoList();

        UUID uuid = roomService.enrollRoom(searchLocationDtoList);

        return "redirect:search-center/"+uuid;
    }

    @GetMapping("/search-center/{id}")
    public String SearchCenter(@PathVariable String id, Model model){

        List<SearchLocationDto> locList = roomService.findLocListByRoomId(id);

        Map<String, Double> center = centerLocationService.centerLocation(locList);

        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "/page/alone_center_location";
    }
}
