package com.ourcenterhere.app.centerlocation.location.controller;

import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDtoList;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
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

    @PostMapping("/alone/save")
    public String SearchCenter(@ModelAttribute LocationDtoList list, Model model){

        System.out.println(list);
        List<LocationDto> locationDtoList = list.getLocationDtoList();
        System.out.println(locationDtoList.toString());

        UUID uuid = roomService.enrollAloneRoom(locationDtoList);

        return "redirect:/alone/save/"+uuid;
    }

    @GetMapping("/alone/save/{id}")
    public String SearchCenter(@PathVariable String id, Model model){

        List<LocationDto> locList = roomService.findLocListByRoomId(id, RoomType.ALONE);

        Map<String, Double> center = centerLocationService.centerLocation(locList);

        model.addAttribute("alone", RoomType.ALONE);
        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "/page/alone_center_location";
    }

}
