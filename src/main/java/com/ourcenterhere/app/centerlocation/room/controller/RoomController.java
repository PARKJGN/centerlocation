package com.ourcenterhere.app.centerlocation.room.controller;

import com.ourcenterhere.app.centerlocation.location.dto.SearchLocationDto;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final LocationService centerLocationService;

    @ResponseBody
    @GetMapping("/getRoomUrl/{id}")
    public String RoomUrl(@PathVariable UUID id){
        UriComponents uriComponents = UriComponentsBuilder
                    .newInstance()
                    .scheme("http")
                    .host("localhost:8080")
                    .path("search-center/")
                    .path(String.valueOf(id))
                    .build();
        return uriComponents.toString();
    }

    @GetMapping("/makeRoom")
    @ResponseBody
    public String makeRoom(){
        String id = roomService.enrollTogetherRoom();
        return id;
    }

    @GetMapping("/together/search-center/{id}")
    public String togetherJoinRoom(@PathVariable String id, Model model){
        List<SearchLocationDto> locList = roomService.findLocListByRoomId(id);
        Map<String, Double> center = null;

        // 저장된 장소가 2개 이상이면 center구하고 클라이언트한테 보여준다.
        if(locList.size()>=2){
            center = centerLocationService.centerLocation(locList);
            model.addAttribute("center", center);
        }
        model.addAttribute("uuid",id);
        model.addAttribute("together", RoomType.TOGETHER);
        model.addAttribute("locationList", locList);
        return "/page/together_center_location";
    }

    @PostMapping("/together/save")
    public String saveLocationInRoom(@Valid SearchLocationDto locationDto){
        System.out.println(locationDto);

        roomService.saveLocation(locationDto);
        System.out.println(locationDto);
        return "redirect:/together/search-center/"+locationDto.getRoomId();
    }

}
