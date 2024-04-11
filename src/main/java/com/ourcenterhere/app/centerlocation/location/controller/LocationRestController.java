package com.ourcenterhere.app.centerlocation.location.controller;

import com.google.gson.Gson;
import com.ourcenterhere.app.centerlocation.Response.ResponseDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDto;
import com.ourcenterhere.app.centerlocation.location.dto.LocationDtoList;
import com.ourcenterhere.app.centerlocation.location.service.LocationService;
import com.ourcenterhere.app.centerlocation.room.entity.RoomType;
import com.ourcenterhere.app.centerlocation.room.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class LocationRestController {

    private final LocationService locationService;
    private final RoomService roomService;

    // 방에서 해당 장소 삭제
    @PostMapping("/together/removeLocation")
    public ResponseEntity<ResponseDto> removeLocationFromRoom(@RequestParam Long id){
        locationService.removeLocation(id);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, HttpStatus.OK.toString()));
    }

    // 해당 방에 있는 장소들 select And 장소들의 가운데 지점
    @PostMapping("/together/selectLocations")
    public ResponseEntity<ResponseDto> selectLocationsFromRoom(@RequestParam String id){

        List<LocationDto> locList= locationService.findLocListByRoomId(id, RoomType.TOGETHER);
        Map<String, Double> center = null;

        if(locList.size()>=2){
            center = locationService.centerLocation(locList);
        }
        Gson gson = new Gson();

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, gson.toJson(center), gson.toJson(locList)));
    }

    // 같이 구하기 장소 추가
    @PostMapping("/together/saveLocation")
    public ResponseEntity<ResponseDto> saveLocationInRoom(@Valid LocationDto locationDto){
        locationService.saveLocation(locationDto);

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, HttpStatus.OK.toString()));
    }

    // 혼자 구하기 방 생성 and 데이터 insert
    @PostMapping("/alone/save")
    public ResponseEntity<ResponseDto> aloneSaveLocation(@ModelAttribute LocationDtoList list, Model model){
        List<LocationDto> locationDtoList = list.getLocationDtoList();

        UUID uuid = roomService.enrollAloneRoom(locationDtoList);

        return ResponseEntity.ok(ResponseDto.res(HttpStatus.OK, HttpStatus.OK.toString(), uuid.toString()));
    }


}
