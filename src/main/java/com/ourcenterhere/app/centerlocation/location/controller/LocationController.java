package com.ourcenterhere.app.centerlocation.location.controller;

import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private final LocationService centerLocationService;
    private final RoomService roomService;

    @PostMapping("/alone")
    public String aloneJoinRoom(@RequestParam String id, Model model){

        List<LocationDto> locList = roomService.findLocListByRoomId(id, RoomType.ALONE);

        Map<String, Double> center = centerLocationService.centerLocation(locList);

        model.addAttribute("alone", RoomType.ALONE);
        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "/page/alone_center_location";
    }

    // 공유된 url 링크로 접속 시
    @GetMapping("/alone/share/{id}")
    public String shareUrl(@PathVariable String id, Model model) throws NoHandlerFoundException {
        roomService.checkShareRoom(id);

        List<LocationDto> locList = roomService.findLocListByRoomId(id, RoomType.ALONE);

        Map<String, Double> center = centerLocationService.centerLocation(locList);

        model.addAttribute("alone", RoomType.ALONE);
        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "/page/alone_center_location";
    }

}
