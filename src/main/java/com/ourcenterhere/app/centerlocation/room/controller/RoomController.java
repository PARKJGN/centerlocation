package com.ourcenterhere.app.centerlocation.room.controller;

import com.ourcenterhere.app.centerlocation.ResponseDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final LocationService locationService;

    @GetMapping("/together/search-center/{id}")
    public String togetherJoinRoom(@PathVariable String id, Model model){
        List<LocationDto> locList = roomService.findLocListByRoomId(id, RoomType.TOGETHER);
        Map<String, Double> center = null;

        // 저장된 장소가 2개 이상이면 center구하고 클라이언트한테 보여준다.
        if(locList.size()>=2){
            center = locationService.centerLocation(locList);
            model.addAttribute("center", center);
        }
        System.out.println(locList.toString());
        model.addAttribute("uuid",id);
        model.addAttribute("together", RoomType.TOGETHER);
        model.addAttribute("locationList", locList);
        return "/page/together_center_location";
    }

    @PostMapping("/together/save")
    public String saveLocationInRoom(@Valid LocationDto locationDto){
        roomService.saveLocation(locationDto);
        return "redirect:/together/search-center/"+locationDto.getRoomId();
    }

    // restapi로 바꾸기
    @DeleteMapping("/together/removeLocation/{id}")
    public String removeLocation(@PathVariable Long id){
        locationService.removeLocation(id);
        return"";
    }

}
