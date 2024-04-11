package com.ourcenterhere.app.centerlocation.room.controller;

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
public class RoomController {

    private final RoomService roomService;
    private final LocationService locationService;

    @PostMapping("/together")
    public String togetherJoinRoom(@RequestParam String id, Model model){
        List<LocationDto> locList = locationService.findLocListByRoomId(id, RoomType.TOGETHER);
        Map<String, Double> center = null;

        // 저장된 장소가 2개 이상이면 center구하고 클라이언트한테 보여준다.
        if(locList.size()>=2){
            center = locationService.centerLocation(locList);
            model.addAttribute("center", center);
        }
        model.addAttribute("uuid",id);
        model.addAttribute("together", RoomType.TOGETHER);
        model.addAttribute("locationList", locList);
        return "page/together_center_location";
    }

    @PostMapping("/alone")
    public String aloneJoinRoom(@RequestParam String id, Model model){

        List<LocationDto> locList = locationService.findLocListByRoomId(id, RoomType.ALONE);

        Map<String, Double> center = locationService.centerLocation(locList);

        model.addAttribute("alone", RoomType.ALONE);
        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "page/alone_center_location";
    }

    // 공유된 url 링크로 접속 시
    @GetMapping("/alone/share/{id}")
    public String shareUrl(@PathVariable String id, Model model) throws NoHandlerFoundException {
        // 유저가 브라우저창에 임의로 id를 적고 들어왔을때 확인용
        // 만약 이상한 id면 404page
        roomService.checkShareRoom(id);

        List<LocationDto> locList = locationService.findLocListByRoomId(id, RoomType.ALONE);

        Map<String, Double> center = locationService.centerLocation(locList);

        model.addAttribute("alone", RoomType.ALONE);
        model.addAttribute("uuid",id);
        model.addAttribute("locationList", locList);
        model.addAttribute("center", center);

        return "page/alone_center_location";
    }

}
